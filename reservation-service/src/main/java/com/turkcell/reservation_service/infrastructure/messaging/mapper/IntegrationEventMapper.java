package com.turkcell.reservation_service.infrastructure.messaging.mapper;

import com.turkcell.reservation_service.domain.event.ReservationCreatedEvent;
import com.turkcell.reservation_service.infrastructure.messaging.event.ReservationCreatedIntegrationEvent;
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
}
