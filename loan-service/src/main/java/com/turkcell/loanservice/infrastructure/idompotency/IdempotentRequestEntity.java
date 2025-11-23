package com.turkcell.loanservice.infrastructure.idompotency;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "idempotent_requests")
public class IdempotentRequestEntity {

    @Id
    private UUID requestId;   // aynı istek için aynı

    private String operation;
    private UUID resourceId;     // loanId
    private Instant createdAt;
}
