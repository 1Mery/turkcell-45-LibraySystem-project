package com.turkcell.notificationservice.application.usecase;

import java.math.BigDecimal;

public record SendFinePaidNotificationCommand(
        String email,
        String userName,
        BigDecimal amount,
        String currency
) {}
