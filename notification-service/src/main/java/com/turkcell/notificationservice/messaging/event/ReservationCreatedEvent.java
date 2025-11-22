package com.turkcell.notificationservice.messaging.event;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ReservationCreatedEvent(
        UUID id,
        OffsetDateTime reservationDate,
        UUID bookId,
        UUID memberId
) {
}
