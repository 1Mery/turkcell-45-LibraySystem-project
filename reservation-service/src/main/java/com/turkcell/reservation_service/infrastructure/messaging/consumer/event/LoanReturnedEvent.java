package com.turkcell.reservation_service.infrastructure.messaging.consumer.event;

import java.time.LocalDate;
import java.util.UUID;

public record LoanReturnedEvent(
                UUID loanId,
                UUID bookId,
                LocalDate returnDate) {
}
