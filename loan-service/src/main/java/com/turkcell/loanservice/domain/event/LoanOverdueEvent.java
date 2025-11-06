package com.turkcell.loanservice.domain.event;

import java.time.Instant;
import java.util.UUID;

public record LoanOverdueEvent(
        UUID loanId,
        UUID userId,
        UUID bookItemId,
        Instant occurredOn
) implements DomainEvent {

}
