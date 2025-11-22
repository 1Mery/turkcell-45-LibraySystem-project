package com.turkcell.fine_service.infrastructure.messaging.producer.event;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record FineCreatedEvent(
        UUID fineId,
        BigDecimal fineAmount,
        OffsetDateTime fineDate,
        String fineStatus,
        String fineType,
        UUID memberId,
        UUID loanId
) {
}
