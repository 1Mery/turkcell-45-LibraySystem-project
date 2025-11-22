package com.turkcell.fine_service.infrastructure.messaging.consumer.idempotency;

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
    @Column(name = "event_id", nullable = false)
    private UUID eventId;

    public ProcessedMessage() {
    }

    public ProcessedMessage(UUID id, UUID eventId) {
        this.id = id;
        this.eventId = eventId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }
}
