package com.turkcell.bookservice.domain.model;

import java.util.Objects;
import java.util.UUID;

public record BookItemId(UUID value) {
    public BookItemId {
        Objects.requireNonNull(value, "BookItemId cannot be null");
    }

    public static BookItemId generate() {
        return new BookItemId(UUID.randomUUID());
    }
}
