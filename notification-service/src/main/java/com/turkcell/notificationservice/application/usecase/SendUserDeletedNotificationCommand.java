package com.turkcell.notificationservice.application.usecase;

public record SendUserDeletedNotificationCommand(
        String email,
        String userName
) {}
