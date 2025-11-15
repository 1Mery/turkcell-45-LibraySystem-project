package com.turkcell.loanservice.application.query;

import com.turkcell.loanservice.application.dto.GetActiveLoanResponse;
import com.turkcell.loanservice.application.mapper.LoanMapper;
import com.turkcell.loanservice.domain.model.Loan;
import com.turkcell.loanservice.domain.model.LoanStatus;
import com.turkcell.loanservice.domain.model.UserId;
import com.turkcell.loanservice.domain.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetActiveLoansQueryHandler {

    private final LoanRepository loanRepository;
    private final LoanMapper mapper;

    public GetActiveLoansQueryHandler(LoanRepository loanRepository, LoanMapper mapper) {
        this.loanRepository = loanRepository;
        this.mapper = mapper;
    }

    public List<GetActiveLoanResponse> activeLoan(GetActiveLoansQuery query) {
        UserId userId = new UserId(query.userId());
        List<Loan> activeLoans = loanRepository.findByUserIdAndStatus(userId, LoanStatus.ACTIVE);

        return activeLoans.stream()
                .map(mapper::toActiveLoanResponse)
                .toList();
    }
}
