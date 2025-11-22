package com.turkcell.fine_service.infrastructure.persistence.repository;

import com.turkcell.fine_service.infrastructure.persistence.entity.outbox.OutboxMessage;
import com.turkcell.fine_service.infrastructure.persistence.entity.outbox.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OutboxRepository extends JpaRepository<OutboxMessage, UUID> {

    List<OutboxMessage> findByStatusOrderByCreatedAtAsc(OutboxStatus status);

}
