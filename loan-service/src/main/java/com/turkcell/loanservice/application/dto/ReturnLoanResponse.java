package com.turkcell.loanservice.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ReturnLoanResponse(
        UUID loanId,
        UUID userId,
        UUID bookItemId,
        LocalDate loanDate,
        LocalDate dueDate,
        LocalDate returnDate,
        String status
) {
}
