package com.turkcell.notificationservice.infrastructure.kafka;

import com.turkcell.notificationservice.application.event.FineEvent;
import com.turkcell.notificationservice.application.mapper.FineEventMapper;
import com.turkcell.notificationservice.application.usecase.SendFineIssuedNotificationHandler;
import com.turkcell.notificationservice.application.usecase.SendFinePaidNotificationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class FineConsumer {

    private final SendFineIssuedNotificationHandler issuedHandler;
    private final SendFinePaidNotificationHandler paidHandler;

    public FineConsumer(SendFineIssuedNotificationHandler issuedHandler,
                        SendFinePaidNotificationHandler paidHandler) {
        this.issuedHandler = issuedHandler;
        this.paidHandler = paidHandler;
    }

    @Bean
    public Consumer<FineEvent> fineEvents() {
        return event -> {
            if ("FINE_ISSUED".equals(event.reason())) {
                issuedHandler.handle(
                        FineEventMapper.toFineIssuedCommand(event)
                );
            } else if ("FINE_PAID".equals(event.reason())) {
                paidHandler.finePaid(
                        FineEventMapper.toFinePaidCommand(event)
                );
            }
        };
    }
}
