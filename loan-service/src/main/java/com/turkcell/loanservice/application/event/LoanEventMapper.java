package com.turkcell.loanservice.application.event;

import com.turkcell.loanservice.domain.model.Loan;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class LoanEventMapper {
    public static LoanEvent toOverdueEvent(
            Loan loan,
            String userEmail,
            String userName,
            String bookTitle
    ) {
        String dueDate = loan.getPeriod().getDueDate().toString();
        int overdueDays = (int) loan.getPeriod().daysLate(LocalDate.now());

        return new LoanEvent(
                userEmail,
                userName,
                bookTitle,
                dueDate,
                overdueDays,
                "LOAN_OVERDUE",
                loan.getId().value().toString()
        );
    }

    public static LoanEvent toReturnedLateEvent(
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
