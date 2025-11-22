package com.turkcell.fine_service.application.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record FineResponse(
        UUID fineId,
        BigDecimal fineAmount,
        OffsetDateTime fineDate,
        String fineStatus,
        String fineType,
        UUID memberId,
        UUID loanId
) {
}
