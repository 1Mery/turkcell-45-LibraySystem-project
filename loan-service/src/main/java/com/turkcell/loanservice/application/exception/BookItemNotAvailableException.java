package com.turkcell.loanservice.application.exception;

import java.util.UUID;

public class BookItemNotAvailableException extends RuntimeException {

    public BookItemNotAvailableException(UUID bookItemId) {
        super("Book item " + bookItemId + " is not available for loan.");
    }
}
