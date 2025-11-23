package com.turkcell.loanservice.infrastructure.persistence.outbox;

public enum OutboxStatus {
    NEW,
    SENT,
    FAILED
}
