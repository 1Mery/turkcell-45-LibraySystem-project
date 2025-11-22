package com.turkcell.fine_service.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record FineId(UUID value) implements Serializable {

    public FineId {
        Objects.requireNonNull(value, "value must not be null");
    }

    public static FineId generate() {
        return new FineId(UUID.randomUUID());
    }
}
