package com.turkcell.notificationservice.application.event;

import java.math.BigDecimal;
import java.time.Instant;

public record FineEvent(
        String userEmail,
        String userName,
        String bookTitle,
        Instant dueDate,
        int daysOverdue,
        BigDecimal amount,
        String currency,
        String reason,
        String fineId
) {
}

