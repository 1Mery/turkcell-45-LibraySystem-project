package com.turkcell.bookservice.application.dto;

import java.util.UUID;

public record BookResponseDto(
        UUID id,
        String title,
        String author,
        String isbn,
        Integer totalPage,
        String publisher,
        String imageUrl,
        UUID categoryId
) {}
