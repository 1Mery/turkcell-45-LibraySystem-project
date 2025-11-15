package com.turkcell.notificationservice.infrastructure.persistence.repository;

import com.turkcell.notificationservice.domain.model.Notification;
import com.turkcell.notificationservice.domain.model.NotificationId;
import com.turkcell.notificationservice.domain.repository.NotificationRepository;
import com.turkcell.notificationservice.infrastructure.persistence.entity.NotificationEntity;
import com.turkcell.notificationservice.infrastructure.persistence.mapper.NotificationEntityMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class NotificationRepositoryAdapter implements NotificationRepository {

    private final JpaNotificationRepository repository;

    public NotificationRepositoryAdapter(JpaNotificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Notification notification) {
        NotificationEntity entity = NotificationEntityMapper.toEntity(notification);
        repository.save(entity);
    }

    @Override
    public Optional<Notification> findById(NotificationId notificationId) {

        return repository.findById(notificationId.value().toString())
                .map(NotificationEntityMapper::toDomain);
    }
}

