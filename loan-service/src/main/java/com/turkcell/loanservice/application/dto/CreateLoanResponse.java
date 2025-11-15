package com.turkcell.loanservice.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public record CreateLoanResponse(
        UUID loanId,
        UUID userId,
        UUID bookItemId,
        LocalDate loanDate,
        LocalDate dueDate,
        String status
) {
}
