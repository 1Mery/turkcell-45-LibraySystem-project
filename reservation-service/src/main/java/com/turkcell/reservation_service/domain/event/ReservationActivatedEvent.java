package com.turkcell.reservation_service.domain.event;

import com.turkcell.reservation_service.domain.model.ReservationId;
import com.turkcell.reservation_service.domain.model.MemberId;
import com.turkcell.reservation_service.domain.model.BookId;

import java.time.OffsetDateTime;

public record ReservationActivatedEvent(
        ReservationId reservationId,
        BookId bookId,
        MemberId memberId,
        OffsetDateTime activatedAt
) {
}

