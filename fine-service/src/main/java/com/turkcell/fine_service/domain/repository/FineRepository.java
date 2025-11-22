package com.turkcell.fine_service.domain.repository;

import com.turkcell.fine_service.domain.model.Fine;
import com.turkcell.fine_service.domain.model.FineId;
import com.turkcell.fine_service.domain.model.LoanId;
import com.turkcell.fine_service.domain.model.MemberId;

import java.util.List;
import java.util.Optional;

public interface FineRepository {

    Fine save(Fine fine);
    List<Fine> findAllPaged(Integer pageIndex, Integer pageSize);
    Optional<Fine> findById(FineId id);
    void deleteById(FineId id);
    void delete(Fine fine);

    List<Fine> findByMemberId(MemberId memberId);
    List<Fine> findByLoanId(LoanId loanId);
}
