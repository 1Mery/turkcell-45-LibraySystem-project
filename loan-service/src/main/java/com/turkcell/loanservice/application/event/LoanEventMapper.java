package com.turkcell.loanservice.application.event;

import com.turkcell.loanservice.domain.model.Loan;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class LoanEventMapper {

    //Yeni ödünç alındığında
    public LoanEvent toCreatedEvent(
            Loan loan,
            String userEmail,
            String userName,
            String bookTitle
    ) {
        return new LoanEvent(
                userEmail,
                userName,
                bookTitle,
                loan.getPeriod().getDueDate().toString(),
                0, // daha gecikme yok
                "LOAN_CREATED",
                loan.getId().value().toString()
        );
    }

    // Süresi geçmiş (scheduler ile tespit)
    public LoanEvent toOverdueEvent(
            Loan loan,
            String userEmail,
            String userName,
            String bookTitle,
            LocalDate today
    ) {
        long overdueDays = ChronoUnit.DAYS.between(
                loan.getPeriod().getDueDate(),
                today
        );
        if (overdueDays < 0) {
            overdueDays = 0; // güvenlik için
        }

        return new LoanEvent(
                userEmail,
                userName,
                bookTitle,
                loan.getPeriod().getDueDate().toString(),
                (int) overdueDays,
                "LOAN_OVERDUE",
                loan.getId().value().toString()
        );
    }

    //Geç iade edildiğinde
    public LoanEvent toLateReturnEvent(
            Loan loan,
            String userEmail,
            String userName,
            String bookTitle,
            LocalDate returnDate
    ) {
        long overdueDays = ChronoUnit.DAYS.between(
                loan.getPeriod().getDueDate(),
                returnDate
        );
        if (overdueDays < 0) {
            overdueDays = 0;
        }

        return new LoanEvent(
                userEmail,
                userName,
                bookTitle,
                loan.getPeriod().getDueDate().toString(),
                (int) overdueDays,
                "LOAN_RETURNED_LATE",
                loan.getId().value().toString()
        );
    }
}