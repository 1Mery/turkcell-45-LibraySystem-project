package com.turkcell.reservation_service.infrastructure.messaging.consumer.event;

import java.util.UUID;

public record BookCopyAddedEvent(UUID bookId) {
}
