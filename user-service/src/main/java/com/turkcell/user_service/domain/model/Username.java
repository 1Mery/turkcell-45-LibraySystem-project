package com.turkcell.user_service.domain.model;

import java.util.Objects;

public record Username(String value) {

    public Username{
        Objects.requireNonNull(value, "Username value cannot null");

        if (value.isBlank()){
            throw new IllegalArgumentException("Username cannot be blank");
        }
        if(value.length() < 3  || value.length() > 12 ){
            throw new IllegalArgumentException("Username must be between 3 and 12 characters");
        }
        if (!value.matches("^[a-zA-Z0-9._]+$")) {
            throw new IllegalArgumentException("Username contains invalid characters");
        }
    }
}
