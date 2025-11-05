package com.turkcell.reservation_service.domain.event;

import java.util.UUID;

public record ReservationCancelledEvent(UUID reservationId) {
}
