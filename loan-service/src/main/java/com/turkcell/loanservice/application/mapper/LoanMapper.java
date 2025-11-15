package com.turkcell.loanservice.application.mapper;

import com.turkcell.loanservice.application.dto.CreateLoanResponse;
import com.turkcell.loanservice.application.dto.GetActiveLoanResponse;
import com.turkcell.loanservice.application.dto.LoanHistoryResponse;
import com.turkcell.loanservice.application.dto.ReturnLoanResponse;
import com.turkcell.loanservice.domain.model.Loan;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LoanMapper {

    public CreateLoanResponse toCreateResponse(Loan loan) {
        return new CreateLoanResponse(
                loan.getId().value(),
                loan.getUserId().value(),
                loan.getBookItemId().value(),
                loan.getPeriod().getLoanDate(),
                loan.getPeriod().getDueDate(),
                loan.getStatus().name()
        );
    }

    public ReturnLoanResponse toReturnResponse(Loan loan) {
        return new ReturnLoanResponse(
                loan.getId().value(),
                loan.getUserId().value(),
                loan.getBookItemId().value(),
                loan.getPeriod().getLoanDate(),
                loan.getPeriod().getDueDate(),
                loan.getReturnDate(),
                loan.getStatus().name()
        );
    }

    public GetActiveLoanResponse toActiveLoanResponse(Loan loan) {
        return new GetActiveLoanResponse(
                loan.getId().value(),
                loan.getBookItemId().value(),
                loan.getPeriod().getLoanDate(),
                loan.getPeriod().getDueDate(),
                loan.getStatus().name()
        );
    }

    public LoanHistoryResponse toLoanHistoryResponse(Loan loan) {
        long daysBorrowed = 0;
        boolean isLate = false;
        long lateDays = 0;

        LocalDate loanDate = loan.getPeriod().getLoanDate();
        LocalDate dueDate = loan.getPeriod().getDueDate();
        LocalDate returnDate = loan.getReturnDate();

        if (returnDate != null) {
            daysBorrowed = java.time.temporal.ChronoUnit.DAYS.between(loanDate, returnDate);
            isLate = loan.getPeriod().isOverdue(returnDate);
            if (isLate) {
                lateDays = java.time.temporal.ChronoUnit.DAYS.between(dueDate, returnDate);
            }
        }

        return new LoanHistoryResponse(
                loan.getId().value(),
                loan.getBookItemId().value(),
                loanDate,
                dueDate,
                returnDate,
                daysBorrowed,
                isLate,
                lateDays
        );
    }
}
