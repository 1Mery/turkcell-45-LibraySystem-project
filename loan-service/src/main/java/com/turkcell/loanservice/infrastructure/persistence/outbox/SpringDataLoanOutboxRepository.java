package com.turkcell.loanservice.infrastructure.persistence.outbox;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataLoanOutboxRepository extends JpaRepository<LoanOutboxEntity, UUID> {

    List<LoanOutboxEntity> findTop50ByStatusOrderByCreatedAtAsc(OutboxStatus status);
}

