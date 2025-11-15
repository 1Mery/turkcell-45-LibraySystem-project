package com.turkcell.notificationservice.domain.repository;

import com.turkcell.notificationservice.domain.model.Notification;
import com.turkcell.notificationservice.domain.model.NotificationId;

import java.util.Optional;

public interface NotificationRepository {
    void save(Notification notification);
    Optional<Notification> findById(NotificationId notificationId);

}
