package com.turkcell.loanservice.application.event;

import java.time.Instant;

public record LoanEvent(
        String userEmail,
        String userName,
        String bookTitle,
        String dueDate,
        int daysOverdue,
        String reason,
        String loanId
) {
}
