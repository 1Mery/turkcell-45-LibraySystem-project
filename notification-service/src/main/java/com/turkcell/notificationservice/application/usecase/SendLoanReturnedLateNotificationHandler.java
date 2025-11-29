package com.turkcell.notificationservice.application.usecase;

import com.turkcell.notificationservice.application.port.NotificationSender;
import com.turkcell.notificationservice.domain.model.*;
import com.turkcell.notificationservice.domain.repository.NotificationRepository;
import org.springframework.stereotype.Component;

@Component
public class SendLoanReturnedLateNotificationHandler {

    private final NotificationRepository repository;
    private final NotificationSender sender;

    public SendLoanReturnedLateNotificationHandler(NotificationRepository repository,
                                                   NotificationSender sender) {
        this.repository = repository;
        this.sender = sender;
    }

    public void loanReturn(SendLoanReturnedLateNotificationCommand command) {

        Recipient recipient = new Recipient(command.userEmail());

        String body = String.format(
                "Ödünç aldığınız '%s' kitabını geç iade ettiniz. " +
                        "Toplam gecikme süresi: %d gün.",
                command.bookTitle(),
                command.daysOverdue()
        );

        Message message = new Message(body);

        Notification notification = Notification.create(
                recipient,
                NotificationType.EMAIL,
                NotificationReason.LOAN_RETURNED_LATE,
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
