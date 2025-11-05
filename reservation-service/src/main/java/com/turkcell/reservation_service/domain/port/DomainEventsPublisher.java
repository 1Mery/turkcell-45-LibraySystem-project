package com.turkcell.reservation_service.domain.port;

import com.turkcell.reservation_service.domain.event.ReservationCancelledEvent;
import com.turkcell.reservation_service.domain.event.ReservationCreatedEvent;

public interface DomainEventsPublisher {

    void publish(ReservationCreatedEvent reservationCreatedEvent);
    void publish(ReservationCancelledEvent reservationCancelledEvent);

}
