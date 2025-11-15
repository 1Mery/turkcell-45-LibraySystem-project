package com.turkcell.loanservice.application.command.returned;

import java.time.LocalDate;
import java.util.UUID;

public record ReturnLoanCommand(
        UUID loanId,
        LocalDate returnDate
) {
}
