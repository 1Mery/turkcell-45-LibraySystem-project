package com.turkcell.notificationservice.infrastructure.persistence.idempotency;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "processed_messages")
public class ProcessedMessage {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "message_id", nullable = false)
    private UUID eventId;

    public ProcessedMessage() {
    }

    public ProcessedMessage(UUID id, UUID eventId) {
        this.id = id;
        this.eventId = eventId;
    }

    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID eventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }
}
