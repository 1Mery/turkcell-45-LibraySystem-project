package com.turkcell.fine_service.infrastructure.persistence.repository;

import com.turkcell.fine_service.infrastructure.persistence.entity.JpaFineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaFineRepository extends JpaRepository<JpaFineEntity, UUID> {

    List<JpaFineEntity> findByMemberId(UUID memberId);
    List<JpaFineEntity> findByLoanId(UUID loanId);
}
