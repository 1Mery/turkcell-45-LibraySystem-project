package com.turkcell.loanservice.domain.model;

import java.util.Objects;
import java.util.UUID;

public record BookItemId(UUID value) {
    public BookItemId{
        Objects.requireNonNull(value,"BookItemId cannot be null");
    }
}
