package com.turkcell.reservation_service.application.exception;

public class ReservationNotFoundException extends RuntimeException{

    public ReservationNotFoundException(String message) {
        super(message);
    }
}
