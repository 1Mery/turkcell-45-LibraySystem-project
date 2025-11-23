package com.turkcell.loanservice.web.exception;

import com.turkcell.loanservice.application.exception.BookItemNotAvailableException;
import com.turkcell.loanservice.application.exception.LoanAlreadyReturnedException;
import com.turkcell.loanservice.application.exception.LoanNotFoundException;
import com.turkcell.loanservice.domain.exception.InvalidLoanOperationException;
import com.turkcell.loanservice.domain.exception.InvalidLoanPeriodException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookItemNotAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBookItemNotAvailable(BookItemNotAvailableException ex) {
        return new ErrorResponse("BOOK_ITEM_NOT_AVAILABLE", ex.getMessage());
    }

    @ExceptionHandler(LoanNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleLoanNotFound(LoanNotFoundException ex) {
        return new ErrorResponse("LOAN_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(LoanAlreadyReturnedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleLoanAlreadyReturned(LoanAlreadyReturnedException ex) {
        return new ErrorResponse("LOAN_ALREADY_RETURNED", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneric(Exception ex) {
        return new ErrorResponse("INTERNAL_SERVER_ERROR", "Unexpected error occurred.");
    }

    @ExceptionHandler(InvalidLoanPeriodException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidLoanPeriod(InvalidLoanPeriodException ex) {
        return new ErrorResponse("INVALID_LOAN_PERIOD", ex.getMessage());
    }

    @ExceptionHandler(InvalidLoanOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidLoanOperation(InvalidLoanOperationException ex) {
        return new ErrorResponse("INVALID_LOAN_OPERATION", ex.getMessage());
    }

}
