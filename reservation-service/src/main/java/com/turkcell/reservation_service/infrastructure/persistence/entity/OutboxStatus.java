package com.turkcell.reservation_service.infrastructure.persistence.entity;

public enum OutboxStatus {
    PENDING,
    SENT,
    FAILED,
}
