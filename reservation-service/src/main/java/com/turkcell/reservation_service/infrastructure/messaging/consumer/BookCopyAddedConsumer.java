package com.turkcell.reservation_service.infrastructure.messaging.consumer;

import com.turkcell.reservation_service.infrastructure.messaging.consumer.event.BookCopyAddedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class BookCopyAddedConsumer {

    @Bean
    public Consumer<BookCopyAddedEvent> loanReturnedEvent() {
        return event -> {
            System.out.println("Kitabın yeni bir kopyası eklendi, availableCopies 1 arttı");
            System.out.println("İlk sıradaki PENDING rezervasyonu ACTIVE durumuna getir");
            System.out.println("Pickup window ata");
            System.out.println("Kullanıcıya bildirim gönder.");

        };
    }
}
