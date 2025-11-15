package com.turkcell.notificationservice.application.port;

import com.turkcell.notificationservice.domain.model.Notification;

// Application bildirim göndermek için sadece bu interfacei bilir
// Nasıl gönderildiğini Infrastructure implemente eder

public interface NotificationSender {

    void send(Notification notification);
}
