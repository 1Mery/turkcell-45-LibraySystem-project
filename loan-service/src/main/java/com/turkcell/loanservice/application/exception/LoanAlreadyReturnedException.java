package com.turkcell.loanservice.application.exception;

public class LoanAlreadyReturnedException extends RuntimeException {

    public LoanAlreadyReturnedException() {
        super("Loan has already been returned.");
    }
}
