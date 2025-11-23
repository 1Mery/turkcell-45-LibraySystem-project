package com.turkcell.notificationservice.application.usecase;

public record SendUserRegisteredNotificationCommand(
        String email,
        String userName
) {}
