package com.turkcell.notificationservice.domain.model;

import java.time.Instant;
import java.util.Objects;

public class Notification {
    private final NotificationId id;
    private  Recipient recipient;
    private  NotificationType type;
    private  NotificationReason reason;

    private Message message;
    private  Instant createdAt;
    private Instant sentAt;

    private NotificationStatus status;

    private Notification(NotificationId id,
                         Recipient recipient,
                         NotificationType type,
                         NotificationReason reason,
                         Message message,
                         Instant createdAt,
                         Instant sentAt,
                         NotificationStatus status) {

        this.id = id;
        this.recipient = recipient;
        this.type = type;
        this.reason = reason;
        this.message = message;
        this.createdAt = createdAt;
        this.sentAt = sentAt;
        this.status = status;

        validate();
    }

    public static Notification create(Recipient recipient,
                                      NotificationType type,
                                      NotificationReason reason,
                                      Message message) {

        return new Notification(
                NotificationId.generate(),
                recipient,
                type,
                reason,
                message,
                Instant.now(),
                null,
                NotificationStatus.PENDING
        );
    }

    public static Notification rehydrate(NotificationId id,
                                         Recipient recipient,
                                         NotificationType type,
                                         NotificationReason reason,
                                         Message message,
                                         Instant createdAt,
                                         Instant sentAt,
                                         NotificationStatus status) {

        return new Notification(
                id,
                recipient,
                type,
                reason,
                message,
                createdAt,
                sentAt,
                status
        );
    }

    private void validate() {
        Objects.requireNonNull(id, "NotificationId cannot be null.");
        Objects.requireNonNull(recipient, "Recipient cannot be null.");
        Objects.requireNonNull(type, "NotificationType cannot be null.");
        Objects.requireNonNull(reason, "NotificationReason cannot be null.");
        Objects.requireNonNull(message, "Notification message cannot be null.");
        Objects.requireNonNull(status, "NotificationStatus cannot be null.");
    }

    public void markSent() {
        if (this.status == NotificationStatus.SENT) {
            throw new IllegalStateException("Notification already marked as SENT.");
        }

        if (this.status == NotificationStatus.FAILED) {
            throw new IllegalStateException("FAILED notifications cannot transition to SENT.");
        }

        this.status = NotificationStatus.SENT;
        this.sentAt = Instant.now();
    }

    public void markFailed() {
        if (this.status == NotificationStatus.SENT) {
            throw new IllegalStateException("SENT notifications cannot become FAILED.");
        }

        this.status = NotificationStatus.FAILED;
    }

    public NotificationId getId() {
        return id;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public NotificationType getType() {
        return type;
    }

    public NotificationReason getReason() {
        return reason;
    }

    public Message getMessage() {
        return message;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getSentAt() {
        return sentAt;
    }

    public NotificationStatus getStatus() {
        return status;
    }
}
