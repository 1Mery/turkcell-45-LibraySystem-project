package com.turkcell.notificationservice.messaging.consumer;

import com.turkcell.notificationservice.messaging.event.ReservationCreatedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class ReservationCreatedConsumer {

    @Bean
    public Consumer<ReservationCreatedEvent> reservationCreated() {
        return event -> {
            System.out.println("Reservation created.");
            System.out.println("Send mail to member");
        };
    }
}
