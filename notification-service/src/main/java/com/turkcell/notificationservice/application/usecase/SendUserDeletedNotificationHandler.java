package com.turkcell.notificationservice.application.usecase;

import com.turkcell.notificationservice.application.port.NotificationSender;
import com.turkcell.notificationservice.domain.model.*;
import com.turkcell.notificationservice.domain.repository.NotificationRepository;
import org.springframework.stereotype.Component;

@Component
public class SendUserDeletedNotificationHandler {

    private final NotificationRepository repository;
    private final NotificationSender sender;

    public SendUserDeletedNotificationHandler(NotificationRepository repository,
                                              NotificationSender sender) {
        this.repository = repository;
        this.sender = sender;
    }

    public void deleteUser(SendUserDeletedNotificationCommand cmd) {

        Message message = new Message(
                "Merhaba " + cmd.userName() +
                        ", kütüphane hesabınız sistemden silinmiştir."
        );

        Notification notification = Notification.create(
                new Recipient(cmd.email()),
                NotificationType.EMAIL,
                NotificationReason.USER_DELETED,
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
