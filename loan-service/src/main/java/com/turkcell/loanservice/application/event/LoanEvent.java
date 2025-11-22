package com.turkcell.loanservice.application.event;


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
