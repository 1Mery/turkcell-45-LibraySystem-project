package com.turkcell.notificationservice.infrastructure.persistence.repository;

import com.turkcell.notificationservice.infrastructure.persistence.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaNotificationRepository extends JpaRepository<NotificationEntity, String> {
}
