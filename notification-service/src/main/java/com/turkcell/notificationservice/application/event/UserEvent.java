package com.turkcell.notificationservice.application.event;

public record UserEvent(
        String userId,
        String userEmail,
        String userName,
        String reason
) {}
