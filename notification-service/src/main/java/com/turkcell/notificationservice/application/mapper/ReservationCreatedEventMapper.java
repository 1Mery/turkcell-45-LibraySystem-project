package com.turkcell.notificationservice.application.mapper;

import com.turkcell.notificationservice.application.event.ReservationCreatedEvent;
import com.turkcell.notificationservice.application.usecase.SendReservationCreatedNotificationCommand;

public class ReservationCreatedEventMapper {

    public static SendReservationCreatedNotificationCommand toCommand(ReservationCreatedEvent event) {
        return new SendReservationCreatedNotificationCommand(
                event.userEmail(),
                event.userName(),
                event.bookTitle(),
                event.reservationDate(),
                event.reservationStatus(),
                event.reservationId()
        );
    }
}
