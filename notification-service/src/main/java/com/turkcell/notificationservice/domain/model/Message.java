package com.turkcell.notificationservice.domain.model;

public record Message(String body) {

    public Message {

        if (body == null || body.isBlank()) {
            throw new IllegalArgumentException("Message body cannot be empty.");
        }
    }
}
