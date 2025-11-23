package com.turkcell.notificationservice.application.usecase;

import com.turkcell.notificationservice.application.port.NotificationSender;
import com.turkcell.notificationservice.domain.model.*;
import com.turkcell.notificationservice.domain.repository.NotificationRepository;
import org.springframework.stereotype.Component;

@Component
public class SendUserRegisteredNotificationHandler {

    private final NotificationRepository repository;
    private final NotificationSender sender;

    public SendUserRegisteredNotificationHandler(NotificationRepository repository,
                                                 NotificationSender sender) {
        this.repository = repository;
        this.sender = sender;
    }

    public void createUser(SendUserRegisteredNotificationCommand cmd) {
        Message message = new Message(
                "Merhaba " + cmd.userName() +
                        ", kütüphane sistemine hoş geldiniz!"
        );

        Notification notification = Notification.create(
                new Recipient(cmd.email()),
                NotificationType.EMAIL,
                NotificationReason.USER_REGISTERED,
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
