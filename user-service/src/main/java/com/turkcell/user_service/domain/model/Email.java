package com.turkcell.user_service.domain.model;

import java.util.Objects;

public record Email(String value) {

    public Email{
        Objects.requireNonNull(value, "Email cannot be null");

        if (value.isBlank()){
            throw new IllegalArgumentException("Email cannot be blank");
        }
        if (!value.matches("^(?=.{1,64}@)[\\p{L}0-9_\\+.-]+(\\.[\\p{L}0-9_\\+.-]+)*@[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$")){
            throw new IllegalArgumentException("Invalid email address");
        }
    }
}
