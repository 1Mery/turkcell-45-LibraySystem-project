package com.turkcell.notificationservice.domain.model;

import com.turkcell.notificationservice.domain.exception.InvalidMessageException;

public record Message(String body) {

    public Message {

        if (body == null || body.isBlank()) {
            throw new InvalidMessageException("Message body cannot be empty.");
        }
    }
}
