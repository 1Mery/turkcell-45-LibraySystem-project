package com.turkcell.fine_service.domain.model;

public enum FineStatus {
    PAID,
    UNPAID;

    public static FineStatus getDefault() {
        return UNPAID;
    }
}
