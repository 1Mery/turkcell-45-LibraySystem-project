package com.turkcell.reservation_service.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record ReservationId(UUID value) implements Serializable {

    public ReservationId{
        Objects.requireNonNull(value, "value must not be null");
    }

    public static ReservationId generate() {
        return new ReservationId(UUID.randomUUID());
    }
}
