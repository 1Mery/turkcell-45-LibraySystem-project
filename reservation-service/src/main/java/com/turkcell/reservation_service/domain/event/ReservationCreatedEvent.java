package com.turkcell.reservation_service.domain.event;

import com.turkcell.reservation_service.domain.model.*;

import java.time.OffsetDateTime;

public record ReservationCreatedEvent (
        ReservationId reservationId,
        OffsetDateTime reservationDate,
        BookId bookId,
        MemberId memberId){
}
