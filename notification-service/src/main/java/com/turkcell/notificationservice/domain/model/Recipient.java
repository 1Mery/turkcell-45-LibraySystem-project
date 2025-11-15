package com.turkcell.notificationservice.domain.model;

public record Recipient(String email) {

    public Recipient {
        if (email != null && !email.isBlank()) {
            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                throw new IllegalArgumentException("Invalid email format.");
            }
        }
    }
}
