package com.turkcell.notificationservice.infrastructure.kafka;

import com.turkcell.notificationservice.application.event.LoanEvent;
import com.turkcell.notificationservice.application.event.NotificationEventRouter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class LoanOverdueConsumer {

    private final NotificationEventRouter router;

    public LoanOverdueConsumer(NotificationEventRouter router) {
        this.router = router;
    }

    @Bean
    public Consumer<LoanEvent> loanOverdueEvent() {
        return event -> router.route(event);
    }
}
