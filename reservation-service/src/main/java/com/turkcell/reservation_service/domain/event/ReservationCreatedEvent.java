package com.turkcell.reservation_service.domain.event;

import com.turkcell.reservation_service.domain.model.BookId;
import com.turkcell.reservation_service.domain.model.ReservationId;
import com.turkcell.reservation_service.domain.model.ReservationStatus;

import java.time.OffsetDateTime;

public record ReservationCreatedEvent(
        ReservationId id,
        OffsetDateTime reservationDate,
        ReservationStatus reservationStatus,
        BookId bookId) {
}
