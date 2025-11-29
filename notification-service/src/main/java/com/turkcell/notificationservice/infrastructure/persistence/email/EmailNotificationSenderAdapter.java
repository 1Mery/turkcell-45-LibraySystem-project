package com.turkcell.notificationservice.infrastructure.persistence.email;

import com.turkcell.notificationservice.application.port.NotificationSender;
import com.turkcell.notificationservice.domain.model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailNotificationSenderAdapter implements NotificationSender {

    @Override
    public void send(Notification notification) {

        // Fake mail gönderimi
        log.info("Kime   : " + notification.getRecipient().email());
        log.info("Mesaj  : " + notification.getMessage().body());

        // Gönderim başarılı
        notification.markSent();
    }
}

