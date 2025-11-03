package com.turkcell.user_service.domain.model;

import java.util.Objects;

public record Email(String value) {

    public Email{
        Objects.requireNonNull(value, "Email cannot be null");

        if (value.isBlank()){
            throw new IllegalArgumentException("Email cannot be blank");
        }
        if (!value.matches("^[A-Za-z0-9._%+-]+@(?:[A-Za-z0-9-]+\\.)*edu\\.tr$")){
            throw new IllegalArgumentException("Invalid email address");
        }
    }
}
