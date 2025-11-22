package com.turkcell.fine_service.infrastructure.messaging.producer.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.fine_service.domain.event.FineCreatedEvent;
import com.turkcell.fine_service.domain.repository.DomainEventsPublisher;
import com.turkcell.fine_service.infrastructure.persistence.entity.outbox.OutboxMessage;
import com.turkcell.fine_service.infrastructure.persistence.repository.OutboxRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DomainEventsPublisherAdapter implements DomainEventsPublisher {

    private final ObjectMapper objectMapper;
    private final OutboxRepository outboxRepository;

    public DomainEventsPublisherAdapter(ObjectMapper objectMapper, OutboxRepository outboxRepository) {
        this.objectMapper = objectMapper;
        this.outboxRepository = outboxRepository;
    }

    @Override
    public void publish(FineCreatedEvent event) {

        OutboxMessage outboxMessage = new OutboxMessage();
        outboxMessage.setAggregateId(event.id().value());
        outboxMessage.setAggregateType("Fine");
        outboxMessage.setEventId(UUID.randomUUID());
        outboxMessage.setEventType("FineCreatedEvent");
        try {
            outboxMessage.setPayloadJson(objectMapper.writeValueAsString(event));
        }catch (JsonProcessingException e) {
            throw new RuntimeException("could not serialize integrationEvent", e);
        }
        outboxRepository.save(outboxMessage);
    }
}
