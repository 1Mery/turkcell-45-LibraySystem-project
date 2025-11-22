package com.turkcell.fine_service.infrastructure.messaging.consumer;

import com.turkcell.fine_service.domain.event.FineCreatedEvent;
import com.turkcell.fine_service.domain.model.*;
import com.turkcell.fine_service.domain.repository.DomainEventsPublisher;
import com.turkcell.fine_service.domain.repository.FineRepository;
import com.turkcell.fine_service.infrastructure.messaging.consumer.event.LoanEvent;
import com.turkcell.fine_service.infrastructure.messaging.consumer.idempotency.ProcessedMessage;
import com.turkcell.fine_service.infrastructure.messaging.consumer.idempotency.ProcessedMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Configuration
public class LoanReturnedEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(LoanReturnedEventConsumer.class);

    private final FineRepository fineRepository;
    private final DomainEventsPublisher domainEventsPublisher;
    private final ProcessedMessageRepository processedMessageRepository;

    public LoanReturnedEventConsumer(FineRepository fineRepository, DomainEventsPublisher domainEventsPublisher, ProcessedMessageRepository processedMessageRepository) {
        this.fineRepository = fineRepository;
        this.domainEventsPublisher = domainEventsPublisher;
        this.processedMessageRepository = processedMessageRepository;
    }

    @Bean
    public Consumer<Message<LoanEvent>> loanReturnedEventConsumer() {
        return message -> {

            UUID eventId = message.getHeaders().get("eventId", UUID.class);

            if (eventId == null) {
                log.error("Event ID is missing in message headers");
                return;
            }
            Optional<ProcessedMessage> existing = processedMessageRepository.findByEventId(eventId);

            if (existing.isPresent()) {
                log.warn("Duplicate event detected: {}, skipping...", eventId);
                return; // hiçbir işlem yapma
            }

            LoanEvent loanEvent = message.getPayload();

            Fine fine = Fine.create(
                    BigDecimal.ZERO,
                    OffsetDateTime.now(),
                    FineStatus.getDefault(),
                    FineType.LATE,
                    new MemberId(loanEvent.memberId()),
                    new LoanId(loanEvent.loanId())
            );
            fine.calculateFineAmount(loanEvent.daysOverdue());

            fineRepository.save(fine);

            FineCreatedEvent event = new FineCreatedEvent(
                    fine.id(),
                    fine.fineAmount(),
                    fine.fineDate(),
                    fine.status(),
                    fine.fineType(),
                    fine.memberId(),
                    fine.loanId()
            );
            domainEventsPublisher.publish(event);

            ProcessedMessage processedMessage = new ProcessedMessage(
                    UUID.randomUUID(),
                    eventId);
            processedMessageRepository.save(processedMessage);
        };
    }
}
