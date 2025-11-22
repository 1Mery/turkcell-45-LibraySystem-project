package com.turkcell.notificationservice.application.usecase;

public record SendLoanReturnedLateNotificationCommand(
        String userEmail,
        String userName,
        String bookTitle,
        int daysOverdue
) {}
