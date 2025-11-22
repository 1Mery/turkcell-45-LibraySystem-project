package com.turkcell.fine_service.infrastructure.messaging.consumer.event;

import java.util.UUID;

public record LoanEvent(
        String userEmail,
        String userName,
        String bookTitle,
        String dueDate,
        int daysOverdue,
        String reason,
        UUID loanId,
        UUID memberId
) {
}
