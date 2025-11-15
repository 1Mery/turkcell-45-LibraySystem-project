package com.turkcell.reservation_service.application.exception;

public class MemberInactiveException extends RuntimeException {
    public MemberInactiveException(String message) {
        super(message);
    }
}
