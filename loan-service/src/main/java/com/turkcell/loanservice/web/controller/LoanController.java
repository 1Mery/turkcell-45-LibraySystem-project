package com.turkcell.loanservice.web.controller;

import com.turkcell.loanservice.application.command.create.CreateLoanCommand;
import com.turkcell.loanservice.application.command.create.CreateLoanCommandHandler;
import com.turkcell.loanservice.application.command.returned.ReturnLoanCommand;
import com.turkcell.loanservice.application.command.returned.ReturnLoanCommandHandler;
import com.turkcell.loanservice.application.dto.CreateLoanResponse;
import com.turkcell.loanservice.application.dto.GetActiveLoanResponse;
import com.turkcell.loanservice.application.dto.LoanHistoryResponse;
import com.turkcell.loanservice.application.dto.ReturnLoanResponse;
import com.turkcell.loanservice.application.query.GetActiveLoansQuery;
import com.turkcell.loanservice.application.query.GetActiveLoansQueryHandler;
import com.turkcell.loanservice.application.query.GetLoanHistoryQuery;
import com.turkcell.loanservice.application.query.GetLoanHistoryQueryHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final CreateLoanCommandHandler createLoanHandler;
    private final ReturnLoanCommandHandler returnLoanHandler;
    private final GetActiveLoansQueryHandler activeLoansHandler;
    private final GetLoanHistoryQueryHandler historyHandler;

    public LoanController(CreateLoanCommandHandler createLoanHandler,
                          ReturnLoanCommandHandler returnLoanHandler,
                          GetActiveLoansQueryHandler activeLoansHandler,
                          GetLoanHistoryQueryHandler historyHandler) {
        this.createLoanHandler = createLoanHandler;
        this.returnLoanHandler = returnLoanHandler;
        this.activeLoansHandler = activeLoansHandler;
        this.historyHandler = historyHandler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateLoanResponse createLoan(@RequestBody CreateLoanCommand command) {
        return createLoanHandler.createLoan(command);
    }

    @PostMapping("/{loanId}/return")
    public ReturnLoanResponse returnLoan(@PathVariable UUID loanId) {
        return returnLoanHandler.returnLoan(new ReturnLoanCommand(loanId, LocalDate.now()));
    }

    @GetMapping("/active/{userId}")
    public List<GetActiveLoanResponse> getActiveLoans(@PathVariable UUID userId) {
        GetActiveLoansQuery query = new GetActiveLoansQuery(userId);
        return activeLoansHandler.activeLoan(query);
    }

    @GetMapping("/history/{userId}")
    public List<LoanHistoryResponse> getLoanHistory(@PathVariable UUID userId) {
        GetLoanHistoryQuery query = new GetLoanHistoryQuery(userId);
        return historyHandler.loanHistory(query);
    }
}
