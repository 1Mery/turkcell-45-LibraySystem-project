package com.turkcell.reservation_service.domain.event;

import com.turkcell.reservation_service.domain.model.ReservationId;

public record ReservationCancelledEvent(ReservationId reservationId) {
}
