package com.turkcell.notificationservice.infrastructure.persistence.email;

import com.turkcell.notificationservice.application.port.NotificationSender;
import com.turkcell.notificationservice.domain.model.Notification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationSenderAdapter implements NotificationSender {

    private final JavaMailSender mailSender;

    public EmailNotificationSenderAdapter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(Notification notification) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notification.getRecipient().email());
        message.setSubject("Kütüphane Bildirimi");
        message.setText(notification.getMessage().body());

        mailSender.send(message);
    }
}
