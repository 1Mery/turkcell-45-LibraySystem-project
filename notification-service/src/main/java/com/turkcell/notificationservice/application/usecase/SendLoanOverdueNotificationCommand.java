package com.turkcell.notificationservice.application.usecase;

import java.time.Instant;

public record SendLoanOverdueNotificationCommand(
        String email,
        String userName,
        String bookTitle,
        Instant dueDate,
        int daysOverdue
){
}
