package com.turkcell.reservation_service.domain.port;

import com.turkcell.reservation_service.domain.event.ReservationActivatedEvent;
import com.turkcell.reservation_service.domain.event.ReservationCancelledEvent;
import com.turkcell.reservation_service.domain.event.ReservationCreatedEvent;
import com.turkcell.reservation_service.domain.event.ReservationExpiredEvent;

public interface DomainEventsPublisher {

    void publish(ReservationCreatedEvent reservationCreatedEvent);

    void publish(ReservationActivatedEvent reservationActivatedEvent);

    void publish(ReservationCancelledEvent reservationCancelledEvent);

    void publish(ReservationExpiredEvent reservationExpiredEvent);

}
