package com.turkcell.notificationservice.application.usecase;

import com.turkcell.notificationservice.application.port.NotificationSender;
import com.turkcell.notificationservice.domain.model.*;
import com.turkcell.notificationservice.domain.repository.NotificationRepository;
import org.springframework.stereotype.Component;

@Component
public class SendFineIssuedNotificationHandler {

    private final NotificationRepository repository;
    private final NotificationSender sender;

    public SendFineIssuedNotificationHandler(NotificationRepository repository,
                                             NotificationSender sender) {
        this.repository = repository;
        this.sender = sender;
    }

    public void handle(SendFineIssuedNotificationCommand command) {

        Recipient recipient = new Recipient(command.email());

        String body = String.format(
                "%s, ödünç aldığınız '%s' kitabını zamanında iade etmediğiniz için " +
                        "%d gün gecikme oluşmuştur. Bu gecikme için ödemeniz gereken ceza tutarı: %s %s.",
                command.userName(),
                command.bookTitle(),
                command.daysOverdue(),
                command.amount().toPlainString(),
                command.currency()
        );

        Message message = new Message(body);

        Notification notification = Notification.create(
                recipient,
                NotificationType.EMAIL,
                NotificationReason.FINE_ISSUED,
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
