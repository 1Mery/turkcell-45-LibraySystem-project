package com.turkcell.notificationservice.application.usecase;

import java.time.OffsetDateTime;
import java.util.UUID;

public record SendReservationCreatedNotificationCommand(
        String email,
        String userName,
        String bookTitle,
        OffsetDateTime reservationDate,
        String reservationStatus,
        UUID reservationId
) {

}