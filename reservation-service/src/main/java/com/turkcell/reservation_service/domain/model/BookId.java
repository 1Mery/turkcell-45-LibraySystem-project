package com.turkcell.reservation_service.domain.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record BookId(UUID value) implements Serializable {

    public BookId{
        Objects.requireNonNull(value,"BookId can not be null..");
    }

    public static BookId generate(){
        return new BookId(UUID.randomUUID());
    }
}
