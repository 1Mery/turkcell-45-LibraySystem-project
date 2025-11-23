package com.turkcell.reservation_service.infrastructure.messaging.consumer.idempotency;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProcessedMessageRepository extends JpaRepository<ProcessedMessage, UUID> {

    boolean existsByEventId(UUID eventId);
}
