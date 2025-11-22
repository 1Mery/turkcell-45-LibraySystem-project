package com.turkcell.fine_service.infrastructure.messaging.consumer.idempotency;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProcessedMessageRepository extends JpaRepository<ProcessedMessage, UUID> {

    Optional<ProcessedMessage> findByEventId(UUID eventId);
}
