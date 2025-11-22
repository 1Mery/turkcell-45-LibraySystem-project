package com.turkcell.fine_service.infrastructure.persistence.entity.outbox;

public enum OutboxStatus {
    PENDING,
    SENT,
    FAILED
}
