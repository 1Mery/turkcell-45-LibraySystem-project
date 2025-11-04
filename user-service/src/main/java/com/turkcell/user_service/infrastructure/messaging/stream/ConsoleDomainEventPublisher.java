package com.turkcell.user_service.infrastructure.messaging.stream;

import com.turkcell.user_service.domain.event.EmailChangedEvent;
import com.turkcell.user_service.domain.port.DomainEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ConsoleDomainEventPublisher implements DomainEventPublisher {

    @Override
    public void publish(EmailChangedEvent event) {
        System.out.println("Email changed: member id= " + event.id() +
                ", new email= " + event.newEmail());
        System.out.println("Notification Service kullanıcıya " +
                "mail adresi değişikliğine dair bildirim gönderir...");
    }
}
