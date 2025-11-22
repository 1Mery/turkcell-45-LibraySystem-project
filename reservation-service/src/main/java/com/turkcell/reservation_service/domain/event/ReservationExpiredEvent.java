package com.turkcell.reservation_service.domain.event;

import com.turkcell.reservation_service.domain.model.ReservationId;

public record ReservationExpiredEvent(ReservationId reservationId) {
}
