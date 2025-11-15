package com.turkcell.notificationservice.domain.model;

public enum NotificationReason {
    RESERVATION_CREATED,
    RESERVATION_CANCELLED,

    LOAN_OVERDUE,
    LOAN_RETURNED_LATE,

    FINE_ISSUED,
    FINE_PAID,

    USER_REGISTERED

    //TODO:event isimleriyle uyumlu olmalı düzenlenecek
}

