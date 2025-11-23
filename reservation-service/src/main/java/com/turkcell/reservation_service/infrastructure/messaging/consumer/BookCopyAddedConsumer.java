package com.turkcell.reservation_service.infrastructure.messaging.consumer;

import com.turkcell.reservation_service.domain.event.ReservationActivatedEvent;
import com.turkcell.reservation_service.domain.model.BookId;
import com.turkcell.reservation_service.domain.model.Reservation;
import com.turkcell.reservation_service.domain.model.ReservationStatus;
import com.turkcell.reservation_service.domain.port.DomainEventsPublisher;
import com.turkcell.reservation_service.domain.port.ReservationRepository;
import com.turkcell.reservation_service.infrastructure.messaging.consumer.event.BookCopyAddedEvent;
import com.turkcell.reservation_service.infrastructure.messaging.consumer.idempotency.ProcessedMessage;
import com.turkcell.reservation_service.infrastructure.messaging.consumer.idempotency.ProcessedMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Configuration
public class BookCopyAddedConsumer {

    private static final Logger logger = LoggerFactory.getLogger(BookCopyAddedConsumer.class);
    private static final String EVENT_ID_HEADER = "eventId";

    private final ReservationRepository reservationRepository;
    private final DomainEventsPublisher domainEventsPublisher;
    private final ProcessedMessageRepository processedMessageRepository;

    public BookCopyAddedConsumer(
            ReservationRepository reservationRepository,
            DomainEventsPublisher domainEventsPublisher,
            ProcessedMessageRepository processedMessageRepository) {
        this.reservationRepository = reservationRepository;
        this.domainEventsPublisher = domainEventsPublisher;
        this.processedMessageRepository = processedMessageRepository;
    }

    @Bean
    public Consumer<Message<BookCopyAddedEvent>> bookCopyAddedEventConsumer() {
        return message -> {
            try {
                processBookCopyAddedEvent(message);
            } catch (Exception e) {
                logger.error("Error processing BookCopyAddedEvent", e);
                // Exception'ı fırlat ki Spring Cloud Stream retry mekanizması devreye girsin
                throw new RuntimeException("Failed to process BookCopyAddedEvent", e);
            }
        };
    }

    @Transactional
    public void processBookCopyAddedEvent(Message<BookCopyAddedEvent> message) {
        UUID eventId = message.getHeaders().get(EVENT_ID_HEADER, UUID.class);
        if (eventId == null) {
            logger.error("Event ID is missing in message headers for BookCopyAddedEvent");
            return;
        }

        // Idempotency check - aynı event'i birden fazla kez işlemeyi önle
        if (processedMessageRepository.existsByEventId(eventId)) {
            logger.warn("Duplicate event detected: {}, skipping...", eventId);
            return;
        }

        BookCopyAddedEvent event = message.getPayload();
        logger.info("Processing BookCopyAddedEvent for bookId: {}, eventId: {}",
                event.bookId(), eventId);

        BookId bookId = new BookId(event.bookId());

        // Kitap için PENDING durumundaki rezervasyonları bul (en eski önce)
        List<Reservation> pendingReservations = reservationRepository
                .findByBookIdAndStatus(bookId, ReservationStatus.PENDING);

        if (pendingReservations.isEmpty()) {
            logger.info("No pending reservations found for bookId: {}", event.bookId());
            // Event'i işlendi olarak işaretle (rezervasyon yoksa bile)
            markEventAsProcessed(eventId);
            return;
        }

        // İlk sıradaki (en eski) PENDING rezervasyonu al
        Reservation firstPendingReservation = pendingReservations.stream()
                .min((r1, r2) -> r1.reservationDate().compareTo(r2.reservationDate()))
                .orElseThrow(() -> new IllegalStateException(
                        "No minimum reservation found despite non-empty list"));

        logger.info("Activating reservation {} for bookId: {}",
                firstPendingReservation.id().value(), event.bookId());

        // Rezervasyonu ACTIVE durumuna getir
        firstPendingReservation.updateReservationStatusAsActive();

        // Pickup window ata (24 saat)
        firstPendingReservation.assignPickupWindow();

        // Rezervasyonu kaydet
        Reservation savedReservation = reservationRepository.save(firstPendingReservation);

        // Domain event publish et
        ReservationActivatedEvent activatedEvent = new ReservationActivatedEvent(
                savedReservation.id(),
                savedReservation.bookId(),
                savedReservation.memberId(),
                OffsetDateTime.now());
        domainEventsPublisher.publish(activatedEvent);

        logger.info("Successfully activated reservation {} for bookId: {}",
                savedReservation.id().value(), event.bookId());

        // Event'i işlendi olarak işaretle
        markEventAsProcessed(eventId);
    }

    private void markEventAsProcessed(UUID eventId) {
        try {
            ProcessedMessage processedMessage = new ProcessedMessage(
                    UUID.randomUUID(),
                    eventId);
            processedMessageRepository.save(processedMessage);
            logger.debug("Marked event as processed: {}", eventId);
        } catch (Exception e) {
            logger.error("Failed to mark event as processed: {}", eventId, e);
            // Bu durumda event tekrar işlenebilir, ama en azından log'ladık
            throw e;
        }
    }
}
