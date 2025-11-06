package com.turkcell.loanservice.domain.event;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record LoanCreatedEvent(
        UUID loanId,
        UUID userId,
        UUID bookItemId,
        LocalDate loanDate,
        LocalDate dueDate,
        Instant occurredOn
) implements DomainEvent {

}
