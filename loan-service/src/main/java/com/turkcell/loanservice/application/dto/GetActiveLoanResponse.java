package com.turkcell.loanservice.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public record GetActiveLoanResponse(
        UUID loanId,
        UUID bookItemId,
        LocalDate loanDate,
        LocalDate dueDate,
        String status
) {
}
