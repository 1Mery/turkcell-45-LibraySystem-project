package com.turkcell.loanservice.domain.model;

import com.turkcell.loanservice.domain.exception.InvalidLoanPeriodException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public final class LoanPeriod {

    private final LocalDate loanDate;
    private final LocalDate dueDate;

    public LoanPeriod(LocalDate loanDate, LocalDate dueDate) {
        if (loanDate == null || dueDate == null)
            throw new InvalidLoanPeriodException("Loan date and due date cannot be null");
        if (loanDate.isAfter(dueDate))
            throw new InvalidLoanPeriodException("Loan date cannot be after due date");
        this.loanDate = loanDate;
        this.dueDate = dueDate;
    }
    // Gecikmiş mi?
    public boolean isOverdue(LocalDate today) {
        Objects.requireNonNull(today, "today cannot be null");
        return today.isAfter(dueDate);
    }

    // Kaç gün gecikmiş?
    public long daysLate(LocalDate today) {
        Objects.requireNonNull(today, "today cannot be null");
        if (!isOverdue(today)) return 0;
        return ChronoUnit.DAYS.between(dueDate, today);
    }

    //  Kaç gün kaldı?
    public long daysRemaining(LocalDate today) {
        Objects.requireNonNull(today, "today cannot be null");
        return ChronoUnit.DAYS.between(today, dueDate);
    }

    // Getters
    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}

