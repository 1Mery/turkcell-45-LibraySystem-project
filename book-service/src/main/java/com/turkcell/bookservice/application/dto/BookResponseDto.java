package com.turkcell.bookservice.application.dto;

import java.util.List;
import java.util.UUID;

public record BookResponseDto(
        UUID id,
        String title,
        String author,
        String isbn,
        Integer totalPage,
        String publisher,
        String imageUrl,
        String categoryName,
        List<BookItemDto> items,
        InventoryDto inventory
) {}
