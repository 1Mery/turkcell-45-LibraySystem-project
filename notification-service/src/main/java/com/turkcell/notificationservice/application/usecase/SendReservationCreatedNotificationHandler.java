package com.turkcell.notificationservice.application.usecase;

import com.turkcell.notificationservice.application.port.NotificationSender;
import com.turkcell.notificationservice.domain.model.*;
import com.turkcell.notificationservice.domain.repository.NotificationRepository;
import org.springframework.stereotype.Component;

@Component
public class SendReservationCreatedNotificationHandler {

    private final NotificationRepository repository;
    private final NotificationSender sender;

    public SendReservationCreatedNotificationHandler(NotificationRepository repository,
                                                     NotificationSender sender) {
        this.repository = repository;
        this.sender = sender;
    }

    public void handle(SendReservationCreatedNotificationCommand command) {

        Recipient recipient = new Recipient(command.email());

        String body = String.format(
                "%s, '%s' kitabı için %s tarihinde bir rezervasyon oluşturdunuz. " +
                        "Rezervasyon durumu: %s.",
                command.userName(),
                command.bookTitle(),
                command.reservationDate(),
                command.reservationStatus()
        );

        Message message = new Message(body);

        Notification notification = Notification.create(
                recipient,
                NotificationType.EMAIL,
                NotificationReason.RESERVATION_CREATED,
                message
        );

        try {
            sender.send(notification);
            notification.markSent();
        } catch (Exception e) {
            notification.markFailed();
        }

        repository.save(notification);
    }
}
