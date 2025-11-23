package com.turkcell.notificationservice.application.excepiton;

public class NotificationSendFailedException extends RuntimeException {

    public NotificationSendFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
