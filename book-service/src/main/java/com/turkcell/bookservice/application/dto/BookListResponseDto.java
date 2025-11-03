package com.turkcell.bookservice.application.dto;

import java.util.UUID;

public record BookListResponseDto(
        UUID id,
        String title,
        String author,
        String publisher,
        String imageUrl
) { }
