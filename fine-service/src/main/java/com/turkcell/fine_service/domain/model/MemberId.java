package com.turkcell.fine_service.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record MemberId(UUID value) implements Serializable {
    public MemberId {
        Objects.requireNonNull(value, "value must not be null");
    }

    public static MemberId generate() {
        return new MemberId(UUID.randomUUID());
    }
}
