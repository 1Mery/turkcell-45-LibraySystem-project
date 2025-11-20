package com.turkcell.reservation_service.infrastructure.persistence.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.reservation_service.infrastructure.messaging.producer.event.ReservationCreatedIntegrationEvent;
import com.turkcell.reservation_service.infrastructure.persistence.entity.OutboxMessage;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OutboxMapper {

    private final ObjectMapper objectMapper;

    public OutboxMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public OutboxMessage toOutbox(ReservationCreatedIntegrationEvent reservationIntegrationEvent) {
        OutboxMessage outboxMessage = new OutboxMessage();
        outboxMessage.setAggregateId(reservationIntegrationEvent.reservationId());
        outboxMessage.setAggregateType("Reservation");
        outboxMessage.setEventId(UUID.randomUUID());
        outboxMessage.setEventType("ReservationCreatedEvent");
        outboxMessage.setPayloadJson(serializeEvent(reservationIntegrationEvent));
        return outboxMessage;
    }

    // from java object(integrationEvent) to serialize as json
    // serialize anında hata çıkabilir bu yüzden writeValueAsString methodu
    // JsonProcessingException
    // hatası fırlatır ve bunu handle etmeni bekler.
    public String serializeEvent(ReservationCreatedIntegrationEvent integrationEvent) {
        String eventJson;
        try {
            eventJson = objectMapper.writeValueAsString(integrationEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("could not serialize integrationEvent", e);
        }
        return eventJson;
    }

}
