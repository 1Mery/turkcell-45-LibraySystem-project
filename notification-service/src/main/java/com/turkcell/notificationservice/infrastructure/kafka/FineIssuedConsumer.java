package com.turkcell.notificationservice.infrastructure.kafka;

import com.turkcell.notificationservice.application.event.FineEvent;
import com.turkcell.notificationservice.application.mapper.FineEventMapper;
import com.turkcell.notificationservice.application.usecase.SendFineIssuedNotificationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class FineIssuedConsumer {

    private final SendFineIssuedNotificationHandler handler;

    public FineIssuedConsumer(SendFineIssuedNotificationHandler handler) {
        this.handler = handler;
    }

    @Bean
    public Consumer<FineEvent> fineIssuedEvent() {
        return event -> handler.handle(
                FineEventMapper.toFineIssuedCommand(event)
        );
    }
}

