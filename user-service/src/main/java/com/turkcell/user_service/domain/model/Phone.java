package com.turkcell.user_service.domain.model;

import java.util.Objects;

public record Phone(String value) {

    public Phone{
        Objects.requireNonNull(value, "phone value cannot be null");

        if(value.isBlank()){
            throw new IllegalArgumentException("Phone cannot be blank");
        }
        if (!value.matches("^(?:\\+90|0)?5\\d{9}$")){
            throw new IllegalArgumentException("Invalid phone number");
        }
    }
}
