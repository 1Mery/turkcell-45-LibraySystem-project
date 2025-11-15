package com.turkcell.notificationservice.application.event;

import java.time.Instant;

public record LoanEvent(
        String userEmail,
        String userName,
        String bookTitle,
        Instant dueDate,
        int daysOverdue,
        String reason,
        String loanId
) {
}
