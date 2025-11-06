package com.turkcell.loanservice.domain.event;

import java.time.Instant;

/**
Application katmanında generic publish yazabilmek için
**/

public interface DomainEvent {
    Instant occurredOn();
}
