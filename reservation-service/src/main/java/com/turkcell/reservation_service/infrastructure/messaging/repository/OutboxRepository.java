package com.turkcell.reservation_service.infrastructure.messaging.repository;

import com.turkcell.reservation_service.infrastructure.messaging.outbox.OutboxMessage;
import com.turkcell.reservation_service.infrastructure.messaging.outbox.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OutboxRepository extends JpaRepository<OutboxMessage, UUID> {

    List<OutboxMessage> findByStatusOrderByCreatedAtAsc(OutboxStatus status);
}
