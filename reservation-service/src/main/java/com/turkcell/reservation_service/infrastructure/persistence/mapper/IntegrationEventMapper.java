package com.turkcell.reservation_service.infrastructure.persistence.mapper;

import com.turkcell.reservation_service.domain.event.ReservationCancelledEvent;
import com.turkcell.reservation_service.domain.event.ReservationCreatedEvent;
import com.turkcell.reservation_service.infrastructure.messaging.producer.event.ReservationCancelledIntegrationEvent;
import com.turkcell.reservation_service.infrastructure.messaging.producer.event.ReservationCreatedIntegrationEvent;
import org.springframework.stereotype.Component;

@Component
public class IntegrationEventMapper {

    public ReservationCreatedIntegrationEvent toIntegrationEvent(ReservationCreatedEvent event) {
        return new ReservationCreatedIntegrationEvent(
                event.reservationId().value(),
                event.reservationDate(),
                event.bookId().value(),
                event.memberId().value()
        );
    }

    public ReservationCancelledIntegrationEvent toIntegrationEvent(ReservationCancelledEvent event) {
        return new ReservationCancelledIntegrationEvent(
                event.reservationId().value()
        );
    }
}
