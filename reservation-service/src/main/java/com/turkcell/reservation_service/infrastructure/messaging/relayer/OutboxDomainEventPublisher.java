package com.turkcell.reservation_service.infrastructure.messaging.relayer;

import com.turkcell.reservation_service.domain.event.ReservationCancelledEvent;
import com.turkcell.reservation_service.domain.event.ReservationCreatedEvent;
import com.turkcell.reservation_service.domain.port.DomainEventsPublisher;
import org.springframework.stereotype.Component;

@Component
public class OutboxDomainEventPublisher implements DomainEventsPublisher {

    @Override
    public void publish(ReservationCreatedEvent reservationCreatedEvent) {

    }

    @Override
    public void publish(ReservationCancelledEvent reservationCancelledEvent) {

    }
}
