package com.turkcell.bookservice.domain.model;

import java.util.UUID;

public record CategoryId(UUID value) {
    public static CategoryId generate() {
        return new CategoryId(UUID.randomUUID());
    }
}