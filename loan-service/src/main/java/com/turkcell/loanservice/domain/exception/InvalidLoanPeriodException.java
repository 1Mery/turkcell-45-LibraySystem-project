package com.turkcell.loanservice.domain.exception;

public class InvalidLoanPeriodException extends RuntimeException {
    public InvalidLoanPeriodException(String message) {
        super(message);
    }
}
