package com.turkcell.loanservice.application.exception;

import java.util.UUID;

public class LoanNotFoundException extends RuntimeException {

    public LoanNotFoundException(UUID loanId) {
        super("Loan with id " + loanId + " was not found.");
    }
}
