package com.turkcell.bookservice.application.dto;

import java.util.UUID;

public record BookItemDto(
        UUID id,
        UUID bookId,
        String title,
        String status
) {
}
