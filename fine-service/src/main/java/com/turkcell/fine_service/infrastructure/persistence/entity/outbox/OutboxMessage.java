package com.turkcell.fine_service.infrastructure.persistence.entity.outbox;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "outbox", indexes = {
        @Index(name = "ix_outbox_event_id", columnList = "eventId", unique = true),
        @Index(name = "ix_outbox_status_created", columnList = "createdAt, status")
})
public class OutboxMessage {

    @Id
    @Column(columnDefinition = "uuid", nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(columnDefinition = "uuid", nullable = false)
    private UUID eventId = UUID.randomUUID();
    private String eventType;
    private String payloadJson;

    @Column(columnDefinition = "uuid", nullable = false)
    private UUID aggregateId;
    private String aggregateType;

    private OffsetDateTime createdAt = OffsetDateTime.now();
    private OffsetDateTime updatedAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OutboxStatus status = OutboxStatus.PENDING;

    private Integer retryCount = 0;

    public OutboxMessage() {
    }

    public OutboxMessage(UUID id, UUID eventId, String eventType, String payloadJson, UUID aggregateId, String aggregateType, OffsetDateTime createdAt, OffsetDateTime updatedAt, OutboxStatus status, Integer retryCount) {
        this.id = id;
        this.eventId = eventId;
        this.eventType = eventType;
        this.payloadJson = payloadJson;
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.retryCount = retryCount;
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

    public String eventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String payloadJson() {
        return payloadJson;
    }

    public void setPayloadJson(String payloadJson) {
        this.payloadJson = payloadJson;
    }

    public UUID aggregateId() {
        return aggregateId;
    }

    public void setAggregateId(UUID aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String aggregateType() {
        return aggregateType;
    }

    public void setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public OffsetDateTime createdAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime updatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public OutboxStatus status() {
        return status;
    }

    public void setStatus(OutboxStatus status) {
        this.status = status;
    }

    public Integer retryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }
}
