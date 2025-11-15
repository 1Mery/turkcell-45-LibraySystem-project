package com.turkcell.loanservice.application.command.create;

import java.time.LocalDate;
import java.util.UUID;

public record CreateLoanCommand(
        UUID userId,
        UUID bookItemId,
        LocalDate loanDate,
        LocalDate dueDate
) {
}
