package com.turkcell.notificationservice.application.mapper;

import com.turkcell.notificationservice.application.event.UserEvent;
import com.turkcell.notificationservice.application.usecase.*;

public class UserEventMapper {

    public static SendUserRegisteredNotificationCommand toRegisteredCommand(UserEvent event) {
        return new SendUserRegisteredNotificationCommand(
                event.userEmail(),
                event.userName()
        );
    }

    public static SendUserDeletedNotificationCommand toDeletedCommand(UserEvent event) {
        return new SendUserDeletedNotificationCommand(
                event.userEmail(),
                event.userName()
        );
    }
}
