package com.turkcell.fine_service.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record LoanId(UUID value) implements Serializable {
    public LoanId {
        Objects.requireNonNull(value, "value must not be null");
    }

    public static LoanId generate() {
        return new LoanId(UUID.randomUUID());
    }
}
