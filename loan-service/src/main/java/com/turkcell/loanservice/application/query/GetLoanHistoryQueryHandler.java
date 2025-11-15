package com.turkcell.loanservice.application.query;

import com.turkcell.loanservice.application.dto.LoanHistoryResponse;
import com.turkcell.loanservice.application.mapper.LoanMapper;
import com.turkcell.loanservice.domain.model.Loan;
import com.turkcell.loanservice.domain.model.LoanStatus;
import com.turkcell.loanservice.domain.model.UserId;
import com.turkcell.loanservice.domain.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetLoanHistoryQueryHandler {

    private final LoanRepository loanRepository;
    private final LoanMapper mapper;

    public GetLoanHistoryQueryHandler(LoanRepository loanRepository, LoanMapper mapper) {
        this.loanRepository = loanRepository;
        this.mapper = mapper;
    }

    public List<LoanHistoryResponse> loanHistory(GetLoanHistoryQuery query) {
        UserId userId = new UserId(query.userId());
        List<Loan> returnedLoans = loanRepository.findByUserIdAndStatus(userId, LoanStatus.RETURNED);

        return returnedLoans.stream()
                .map(mapper::toLoanHistoryResponse)
                .toList();
    }
}
