package com.turkcell.loanservice.domain.model;

import java.time.LocalDate;
import java.util.Objects;

public class Loan {
    private final LoanId id;
    private final UserId userId;
    private final BookItemId bookItemId;
    private LoanPeriod period;
    private LoanStatus status;
    private LocalDate returnDate;

    private Loan(LoanId id, UserId userId, BookItemId bookItemId, LoanPeriod period, LoanStatus status, LocalDate returnDate) {
        this.id = id;
        this.userId = userId;
        this.bookItemId = bookItemId;
        this.period = period;
        this.status = status;
        this.returnDate = returnDate;
    }

    public static Loan create(UserId userId, BookItemId bookItemId, LoanPeriod period) {
        validateCreate(userId, bookItemId, period);
        return new Loan(LoanId.generate(), userId, bookItemId, period, LoanStatus.ACTIVE, null);
    }

    private static void validateCreate(UserId userId, BookItemId bookItemId, LoanPeriod period) {
        Objects.requireNonNull(userId, "UserId cannot be null");
        Objects.requireNonNull(bookItemId, "BookItemId cannot be null");
        Objects.requireNonNull(period, "LoanPeriod cannot be null");
    }

    public static Loan rehydrate(LoanId id, UserId userId, BookItemId bookItemId,
                                 LoanPeriod period, LoanStatus status, LocalDate returnDate) {
        return new Loan(id, userId, bookItemId, period, status, returnDate);
    }

    // Kitap iade edildiğinde çağrılır
    public void markReturned(LocalDate returnDate) {
        if (status != LoanStatus.ACTIVE)
            throw new IllegalStateException("Only ACTIVE loans can be returned");
        if (returnDate == null)
            throw new IllegalArgumentException("Return date cannot be null");

        this.returnDate = returnDate;
        this.status = LoanStatus.RETURNED;
    }

    //Geciken ödünçler için
    public void markOverdue() {
        if (status == LoanStatus.ACTIVE){
            this.status = LoanStatus.OVERDUE;
        }
    }


    public LoanId getId() {
        return id;
    }

    public UserId getUserId() {
        return userId;
    }

    public BookItemId getBookItemId() {
        return bookItemId;
    }

    public LoanPeriod getPeriod() {
        return period;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
}