package com.turkcell.notificationservice.application.usecase;

import com.turkcell.notificationservice.application.port.NotificationSender;
import com.turkcell.notificationservice.domain.model.*;
import com.turkcell.notificationservice.domain.repository.NotificationRepository;
import org.springframework.stereotype.Component;

@Component
// Use case implementasyonu (Command Handler)
public class SendLoanOverdueNotificationHandler {

    private final NotificationRepository repository;
    private final NotificationSender sender;

    // Handlerın dependencyleri constructordan gelir
    public SendLoanOverdueNotificationHandler(
            NotificationRepository repository,
            NotificationSender sender
    ) {
        this.repository = repository;
        this.sender = sender;
    }

    public void loanNotification(SendLoanOverdueNotificationCommand command) {
        Recipient recipient = new Recipient(
                command.email()
        );

        String body = String.format(
                "%s, ödünç aldığınız '%s' kitabını %s tarihinde iade etmeliydiniz. " +
                        "%d gündür gecikmiş durumdasınız. Lütfen en kısa sürede iade ediniz.",
                command.userName(),
                command.bookTitle(),
                command.dueDate(),
                command.daysOverdue()
        );

        Message message = new Message(body);

        Notification notification = Notification.create(
                recipient,
                NotificationType.EMAIL,
                NotificationReason.LOAN_OVERDUE,
                message
        );

        try {
            // Email gönderme girişimi
            sender.send(notification);

            //  Domain state SENT
            notification.markSent();

        } catch (Exception e) {
            // Eğer sender.send sırasında bir hata olursa
            //Domain state FAILED
            notification.markFailed();
        }
        repository.save(notification);

    }
}
