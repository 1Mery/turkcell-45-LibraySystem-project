package com.turkcell.reservation_service.infrastructure.messaging.consumer.event;

import java.util.UUID;

public record BookCreatedEvent(
        UUID bookId,
        String title,
        String author,
        String isbn,
        Integer totalPage,
        String publisher,
        String imageUrl,
        UUID categoryId
) {
}
