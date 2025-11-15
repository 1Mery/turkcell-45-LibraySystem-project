package com.turkcell.notificationservice.application.usecase;

public record SendLoanReturnedLateNotificationCommand(
        String email,
        String bookTitle,
        int daysOverdue
) {}
