package com.turkcell.bookservice.application.exception;

import java.util.UUID;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(UUID id) {
        super("Book with id " + id + " was not found.");
    }
}
