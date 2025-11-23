package com.turkcell.loanservice.infrastructure.persistence.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.loanservice.application.event.LoanEvent;
import com.turkcell.loanservice.application.port.LoanOutboxPort;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class LoanOutboxJpaAdapter implements LoanOutboxPort {

    private final SpringDataLoanOutboxRepository repository;
    private final ObjectMapper objectMapper;

    public LoanOutboxJpaAdapter(SpringDataLoanOutboxRepository repository,
                                ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void saveEvent(String eventType, LoanEvent event) {
        try {
            String payload = objectMapper.writeValueAsString(event);

            LoanOutboxEntity entity = new LoanOutboxEntity();
            entity.setAggregateType("LOAN");
            entity.setAggregateId(UUID.fromString(event.loanId()));
            entity.setEventType(eventType);
            entity.setPayload(payload);
            entity.setStatus(OutboxStatus.NEW);
            entity.setCreatedAt(Instant.now());

            repository.save(entity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save outbox event", e);
        }
    }
}
