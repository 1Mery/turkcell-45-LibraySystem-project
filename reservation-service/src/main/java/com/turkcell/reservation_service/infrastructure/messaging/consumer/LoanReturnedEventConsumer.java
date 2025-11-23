package com.turkcell.reservation_service.infrastructure.messaging.consumer;

import com.turkcell.reservation_service.domain.event.ReservationActivatedEvent;
import com.turkcell.reservation_service.domain.model.BookId;
import com.turkcell.reservation_service.domain.model.Reservation;
import com.turkcell.reservation_service.domain.model.ReservationStatus;
import com.turkcell.reservation_service.domain.port.DomainEventsPublisher;
import com.turkcell.reservation_service.domain.port.ReservationRepository;
import com.turkcell.reservation_service.infrastructure.messaging.consumer.event.LoanReturnedEvent;
import com.turkcell.reservation_service.infrastructure.messaging.consumer.idempotency.ProcessedMessage;
import com.turkcell.reservation_service.infrastructure.messaging.consumer.idempotency.ProcessedMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Configuration
public class LoanReturnedEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(LoanReturnedEventConsumer.class);

    private final ReservationRepository reservationRepository;
    private final DomainEventsPublisher domainEventsPublisher;
    private final ProcessedMessageRepository processedMessageRepository;

    public LoanReturnedEventConsumer(
            ReservationRepository reservationRepository,
            DomainEventsPublisher domainEventsPublisher,
            ProcessedMessageRepository processedMessageRepository) {
        this.reservationRepository = reservationRepository;
        this.domainEventsPublisher = domainEventsPublisher;
        this.processedMessageRepository = processedMessageRepository;
    }

    @Bean
    public Consumer<LoanReturnedEvent> loanReturnedEventConsumer() {
        return event -> {

            try {

                UUID eventId = event.loanId();
                if (processedMessageRepository.existsByEventId(eventId)) {
                    logger.warn("LoanReturnedEvent already processed for loanId: {}", event.loanId());
                    return;
                }

                logger.info("Processing LoanReturnedEvent for loanId: {}, bookId: {}",
                        event.loanId(), event.bookId());

                BookId bookId = new BookId(event.bookId());

                // Kitap için PENDING durumundaki rezervasyonları bul (en eski önce)
                List<Reservation> pendingReservations = reservationRepository
                        .findByBookIdAndStatus(bookId, ReservationStatus.PENDING);


                // İlk sıradaki (en eski) PENDING rezervasyonu al
                Reservation firstPendingReservation = pendingReservations.stream()
                        .min((r1, r2) -> r1.reservationDate().compareTo(r2.reservationDate()))
                        .orElseThrow();

                logger.info("Activating reservation {} for bookId: {} (loan returned)",
                        firstPendingReservation.id().value(), event.bookId());

                // Rezervasyonu ACTIVE durumuna getir
                firstPendingReservation.updateReservationStatusAsActive();

                // Pickup window ata (24 saat)
                firstPendingReservation.assignPickupWindow();

                // Rezervasyonu kaydet
                Reservation reservation = reservationRepository.save(firstPendingReservation);

                // Domain event publish et
                ReservationActivatedEvent activatedEvent = new ReservationActivatedEvent(
                        reservation.id(),
                        reservation.bookId(),
                        reservation.memberId(),
                        OffsetDateTime.now()
                );
                domainEventsPublisher.publish(activatedEvent);

                logger.info("Successfully activated reservation {} for bookId: {} (loan returned)",
                        reservation.id().value(), event.bookId());

                ProcessedMessage processedMessage = new ProcessedMessage(
                        UUID.randomUUID(),
                        eventId
                );
                processedMessageRepository.save(processedMessage);
            } catch (Exception e) {
                logger.error("Error processing LoanReturnedEvent for loanId: {}, bookId: {}",
                        event.loanId(), event.bookId(), e);
                // Event'i tekrar işlemek için exception'ı fırlatabiliriz veya dead letter queue'ya gönderebiliriz
                throw new RuntimeException("Failed to process LoanReturnedEvent", e);
            }
        };
    }
}

