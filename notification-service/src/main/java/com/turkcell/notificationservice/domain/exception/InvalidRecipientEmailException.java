package com.turkcell.notificationservice.domain.exception;

public class InvalidRecipientEmailException extends RuntimeException {

    public InvalidRecipientEmailException(String message) {
        super(message);
    }
}
