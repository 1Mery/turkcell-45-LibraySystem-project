package com.turkcell.loanservice.infrastructure.persistence.repository;

import com.turkcell.loanservice.domain.model.LoanStatus;
import com.turkcell.loanservice.infrastructure.persistence.entity.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataLoanRepository extends JpaRepository<LoanEntity, UUID> {
    List<LoanEntity> findByUserIdAndStatus(UUID userId, LoanStatus status);
    List<LoanEntity> findAllByStatus(LoanStatus status);
}
