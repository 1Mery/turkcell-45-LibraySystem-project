package com.turkcell.reservation_service.domain.model;

public enum ReservationStatus {
    PENDING,
    ACTIVE,
    /**
     * Reservation is active but not completed
     * it will be fulfilled when completed
     */
    PICKUP,
    /**
     * Pickup process has started for the Member
     */
    CANCELLED,
    /**
     * Reservation has been cancelled
     * by Member or the system.
     */
    FULFILLED;
    /**
     * Reservation has been successfully completed.
     */

    public static ReservationStatus getDefault(){
        return PENDING;
    }
}
