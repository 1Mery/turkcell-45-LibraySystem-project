package com.turkcell.reservation_service.infrastructure.messaging.producer.event;

import java.util.UUID;

public record ReservationCancelledIntegrationEvent(UUID reservationId) {
}
