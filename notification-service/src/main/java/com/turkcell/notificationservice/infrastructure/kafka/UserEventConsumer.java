package com.turkcell.notificationservice.infrastructure.kafka;

import com.turkcell.notificationservice.application.event.UserEvent;
import com.turkcell.notificationservice.application.mapper.UserEventMapper;
import com.turkcell.notificationservice.application.usecase.SendUserDeletedNotificationHandler;
import com.turkcell.notificationservice.application.usecase.SendUserRegisteredNotificationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class UserEventConsumer {

    private final SendUserRegisteredNotificationHandler registeredHandler;
    private final SendUserDeletedNotificationHandler deletedHandler;

    public UserEventConsumer(SendUserRegisteredNotificationHandler registeredHandler,
                             SendUserDeletedNotificationHandler deletedHandler) {
        this.registeredHandler = registeredHandler;
        this.deletedHandler = deletedHandler;
    }

    @Bean
    public Consumer<UserEvent> userEvents() {
        return event -> {
            if ("USER_REGISTERED".equals(event.reason())) {
                registeredHandler.createUser(
                        UserEventMapper.toRegisteredCommand(event)
                );
            } else if ("USER_DELETED".equals(event.reason())) {
                deletedHandler.deleteUser(
                        UserEventMapper.toDeletedCommand(event)
                );
            }
        };
    }
}
