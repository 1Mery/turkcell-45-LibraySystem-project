package com.turkcell.notificationservice.application.usecase;

import com.turkcell.notificationservice.application.port.NotificationSender;
import com.turkcell.notificationservice.domain.model.*;
import com.turkcell.notificationservice.domain.repository.NotificationRepository;
import org.springframework.stereotype.Component;

@Component
public class SendFinePaidNotificationHandler {

    private final NotificationRepository repository;
    private final NotificationSender sender;

    public SendFinePaidNotificationHandler(NotificationRepository repository,
                                           NotificationSender sender) {
        this.repository = repository;
        this.sender = sender;
    }

    public void finePaid(SendFinePaidNotificationCommand cmd) {

        Message message = new Message(
                "Merhaba " + cmd.userName() +
                        ", " + cmd.amount() + " " + cmd.currency() +
                        " tutarındaki cezanız başarıyla ödenmiştir."
        );

        Notification notification = Notification.create(
                new Recipient(cmd.email()),
                NotificationType.EMAIL,
                NotificationReason.FINE_PAID,
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
