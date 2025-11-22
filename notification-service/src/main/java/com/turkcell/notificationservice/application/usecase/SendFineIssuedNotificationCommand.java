package com.turkcell.notificationservice.application.usecase;

import java.math.BigDecimal;
import java.time.Instant;

public record SendFineIssuedNotificationCommand(
        String email,
        String userName,
        String bookTitle,
        Instant dueDate,
        int daysOverdue,
        BigDecimal amount,
        String currency
) {
}
