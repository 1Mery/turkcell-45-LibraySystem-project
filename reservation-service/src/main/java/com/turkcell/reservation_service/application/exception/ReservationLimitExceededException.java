package com.turkcell.reservation_service.application.exception;

public class ReservationLimitExceededException extends RuntimeException {
    
    public ReservationLimitExceededException(String message) {
        super(message);
    }
}


