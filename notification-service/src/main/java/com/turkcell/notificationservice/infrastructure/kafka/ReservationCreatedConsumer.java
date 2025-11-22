package com.turkcell.notificationservice.infrastructure.kafka;

import com.turkcell.notificationservice.application.event.ReservationCreatedEvent;
import com.turkcell.notificationservice.application.mapper.ReservationCreatedEventMapper;
import com.turkcell.notificationservice.application.usecase.SendReservationCreatedNotificationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ReservationCreatedConsumer {

    private final SendReservationCreatedNotificationHandler handler;

    public ReservationCreatedConsumer(SendReservationCreatedNotificationHandler handler) {
        this.handler = handler;
    }

    @Bean
    public Consumer<ReservationCreatedEvent> reservationCreated() {
        return event ->
            handler.handle(
                    ReservationCreatedEventMapper.toCommand(event)
            );
        }
    }
