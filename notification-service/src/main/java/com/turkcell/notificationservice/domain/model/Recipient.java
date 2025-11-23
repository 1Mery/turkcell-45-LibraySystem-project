package com.turkcell.notificationservice.domain.model;

import com.turkcell.notificationservice.domain.exception.InvalidRecipientEmailException;

public record Recipient(String email) {

    public Recipient {
        if (email != null && !email.isBlank()) {
            if(!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")){
                throw new InvalidRecipientEmailException("Invalid email format.");
            }
        }
    }
}
