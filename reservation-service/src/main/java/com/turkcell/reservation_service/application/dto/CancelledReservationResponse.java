package com.turkcell.reservation_service.application.dto;

import java.util.UUID;

public record CancelledReservationResponse(
        UUID cancelledReservationId,
        String reservationStatus) {
}
