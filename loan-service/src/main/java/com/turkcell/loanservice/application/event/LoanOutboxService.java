package com.turkcell.loanservice.application.event;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.loanservice.infrastructure.persistence.outbox.LoanOutboxEntity;
import com.turkcell.loanservice.infrastructure.persistence.outbox.OutboxStatus;
import com.turkcell.loanservice.infrastructure.persistence.outbox.SpringDataLoanOutboxRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class LoanOutboxService {

    private final SpringDataLoanOutboxRepository repository;
    private final ObjectMapper objectMapper;

    public LoanOutboxService(SpringDataLoanOutboxRepository repository,
                             ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

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
