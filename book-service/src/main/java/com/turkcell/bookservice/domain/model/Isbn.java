package com.turkcell.bookservice.domain.model;

import java.util.Objects;

public record Isbn(String isbn) {

    public Isbn {
        Objects.requireNonNull(isbn, "ISBN cannot be null");

        if (!isbn.matches("\\d{13}")) {
            throw new IllegalArgumentException("ISBN must be a 13-digit number");
        }
    }
}