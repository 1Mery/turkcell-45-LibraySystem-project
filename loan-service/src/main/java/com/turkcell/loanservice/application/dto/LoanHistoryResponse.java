package com.turkcell.loanservice.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public record LoanHistoryResponse(
        UUID loanId,
        UUID bookItemId,
        LocalDate loanDate,
        LocalDate dueDate,
        LocalDate returnDate,
        long daysBorrowed,//kullanıcıya kitabı kaç gün ödünç tuttuğunu göstermek için
        boolean isLate,
        long lateDays
) {
}
