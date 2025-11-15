package com.turkcell.notificationservice.infrastructure.persistence.mapper;

import com.turkcell.notificationservice.domain.model.*;
import com.turkcell.notificationservice.infrastructure.persistence.entity.NotificationEntity;
import com.turkcell.notificationservice.infrastructure.persistence.entity.RecipientEmbeddable;

import java.util.UUID;

public class NotificationEntityMapper {

    public static NotificationEntity toEntity(Notification notification) {

        Recipient recipient = notification.getRecipient();

        RecipientEmbeddable embeddable = new RecipientEmbeddable();
        embeddable.setEmail(recipient.email());

        NotificationEntity entity = new NotificationEntity();
        entity.setId(notification.getId().value().toString());
        entity.setType(notification.getType().name());
        entity.setReason(notification.getReason().name());
        entity.setStatus(notification.getStatus().name());
        entity.setRecipient(embeddable);
        entity.setMessageBody(notification.getMessage().body());
        entity.setCreatedAt(notification.getCreatedAt());
        entity.setSentAt(notification.getSentAt());

        return entity;
    }

    public static Notification toDomain(NotificationEntity entity) {

        RecipientEmbeddable recipient = entity.getRecipient();

        return Notification.rehydrate(
                new NotificationId(UUID.fromString(entity.getId())),
                new Recipient(
                        recipient.getEmail()
                ),
                NotificationType.valueOf(entity.getType()),
                NotificationReason.valueOf(entity.getReason()),
                new Message(entity.getMessageBody()),
                entity.getCreatedAt(),
                entity.getSentAt(),
                NotificationStatus.valueOf(entity.getStatus())
        );
    }
}

