package com.turkcell.user_service.domain.port;

import com.turkcell.user_service.domain.event.EmailChangedEvent;

public interface DomainEventPublisher {

    void publish(EmailChangedEvent event);
}
