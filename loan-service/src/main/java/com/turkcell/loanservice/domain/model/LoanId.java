package com.turkcell.loanservice.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record LoanId(UUID value) implements Serializable {
    public LoanId {
        Objects.requireNonNull(value,"LoanId cannot be null.");
    }
    public static LoanId generate() {
        return new LoanId(UUID.randomUUID());
    }
}
