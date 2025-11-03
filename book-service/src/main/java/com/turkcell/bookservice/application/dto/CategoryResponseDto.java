package com.turkcell.bookservice.application.dto;

import java.util.UUID;

public record CategoryResponseDto(
        UUID categoryId,
        String name
) {}
