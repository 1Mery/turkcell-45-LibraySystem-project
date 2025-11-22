package com.turkcell.notificationservice.application.event;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ReservationCreatedEvent(
        UUID reservationId,
        OffsetDateTime reservationDate,
        String reservationStatus,
        UUID bookId,

        String userEmail,
        String userName,
        String bookTitle
) {
}