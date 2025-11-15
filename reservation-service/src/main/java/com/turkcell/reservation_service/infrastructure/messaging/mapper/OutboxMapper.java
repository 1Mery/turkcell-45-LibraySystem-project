package com.turkcell.reservation_service.infrastructure.messaging.mapper;

import com.turkcell.reservation_service.infrastructure.messaging.event.ReservationCreatedIntegrationEvent;
import com.turkcell.reservation_service.infrastructure.messaging.outbox.OutboxMessage;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OutboxMapper {

    public OutboxMessage toOutbox(ReservationCreatedIntegrationEvent reservationIntegrationEvent) {
        OutboxMessage outboxMessage = new OutboxMessage();
        outboxMessage.setAggregateId(reservationIntegrationEvent.reservationId());
        outboxMessage.setAggregateType("Reservation");
        outboxMessage.setEventId(UUID.randomUUID());
        outboxMessage.setEventType("ReservationCreatedEvent");
        return outboxMessage;
    }
}
