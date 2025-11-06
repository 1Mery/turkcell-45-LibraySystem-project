package com.turkcell.loanservice.domain.model;

import java.util.Objects;
import java.util.UUID;

public record UserId(UUID value) {
    public UserId{
        Objects.requireNonNull(value,"UserId cannot be null");
    }
}
