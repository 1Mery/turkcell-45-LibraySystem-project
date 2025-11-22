package com.turkcell.fine_service.domain.event;

import com.turkcell.fine_service.domain.model.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


public record FineCreatedEvent(
        FineId id,
        BigDecimal fineAmount,
        OffsetDateTime fineDate,
        FineStatus fineStatus,
        FineType fineType,
        MemberId memberId,
        LoanId loanId

) {
}
