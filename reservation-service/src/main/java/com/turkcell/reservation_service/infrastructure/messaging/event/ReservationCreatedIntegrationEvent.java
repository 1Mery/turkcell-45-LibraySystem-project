package com.turkcell.reservation_service.infrastructure.messaging.event;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ReservationCreatedIntegrationEvent(
        UUID reservationId,
        OffsetDateTime reservationDate,
        UUID bookId,
        UUID memberId
) {
}
