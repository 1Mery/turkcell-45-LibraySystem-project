package com.turkcell.loanservice.domain.repository;

import com.turkcell.loanservice.domain.model.Loan;
import com.turkcell.loanservice.domain.model.LoanId;

import java.util.List;
import java.util.Optional;

public interface LoanRepository {
    Loan save(Loan loan);
    Optional<Loan> findById(LoanId loanId);
    List<Loan> findAllPaged(Integer pageIndex,Integer pageSize);
    void delete(LoanId loanId);
}
