package com.turkcell.notificationservice.application.mapper;

import com.turkcell.notificationservice.application.event.FineEvent;
import com.turkcell.notificationservice.application.usecase.SendFineIssuedNotificationCommand;

public class FineEventMapper {

    public static SendFineIssuedNotificationCommand toFineIssuedCommand(FineEvent event) {
        return new SendFineIssuedNotificationCommand(
                event.userEmail(),
                event.userName(),
                event.bookTitle(),
                event.dueDate(),
                event.daysOverdue(),
                event.amount(),
                event.currency()
        );
    }
}
