package com.turkcell.fine_service.domain.repository;

import com.turkcell.fine_service.domain.event.FineCreatedEvent;

public interface DomainEventsPublisher {
    void publish(FineCreatedEvent event);
}
