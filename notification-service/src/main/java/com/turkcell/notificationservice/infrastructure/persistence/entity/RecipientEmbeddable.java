package com.turkcell.notificationservice.infrastructure.persistence.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class RecipientEmbeddable {

    private String email;


    public RecipientEmbeddable() {
    }

    public RecipientEmbeddable(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
