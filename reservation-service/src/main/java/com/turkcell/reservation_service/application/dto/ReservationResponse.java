package com.turkcell.reservation_service.application.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ReservationResponse(
        UUID reservationId,
        UUID memberId,
        UUID bookId,
        OffsetDateTime reservationDate,
        OffsetDateTime expireAt,
        String reservationStatus,
        String cancellationReason,
        OffsetDateTime lastModifiedAt
) {
}
