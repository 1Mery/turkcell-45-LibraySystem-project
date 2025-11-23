package com.turkcell.loanservice.infrastructure.persistence.outbox;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "loan_outbox")
public class LoanOutboxEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String aggregateType; // LOAN
    private UUID aggregateId;     // loanId

    private String eventType;
    @Lob
    private String payload;

    @Enumerated(EnumType.STRING)
    private OutboxStatus status;

    private Instant createdAt;
    private Instant lastAttemptAt;


}
