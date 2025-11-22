package com.turkcell.fine_service.infrastructure.persistence.entity;

import com.turkcell.fine_service.domain.model.FineStatus;
import com.turkcell.fine_service.domain.model.FineType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "fines")
public class JpaFineEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "fine_amount")
    private BigDecimal fineAmount;
    @Column(name = "fine_date")
    private OffsetDateTime fineDate;

    @Enumerated(EnumType.STRING)
    private FineStatus fineStatus = FineStatus.UNPAID;
    @Enumerated(EnumType.STRING)
    private FineType fineType;

    @Column(name = "member_id")
    private UUID memberId;
    @Column(name = "loan_id")
    private UUID loanId;

    public JpaFineEntity() {
    }

    public JpaFineEntity(UUID id, BigDecimal fineAmount, OffsetDateTime fineDate, FineStatus fineStatus, FineType fineType, UUID memberId, UUID loanId) {
        this.id = id;
        this.fineAmount = fineAmount;
        this.fineDate = fineDate;
        this.fineStatus = fineStatus;
        this.fineType = fineType;
        this.memberId = memberId;
        this.loanId = loanId;
    }

    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal fineAmount() {
        return fineAmount;
    }

    public void setFineAmount(BigDecimal fineAmount) {
        this.fineAmount = fineAmount;
    }

    public OffsetDateTime fineDate() {
        return fineDate;
    }

    public void setFineDate(OffsetDateTime fineDate) {
        this.fineDate = fineDate;
    }

    public FineStatus fineStatus() {
        return fineStatus;
    }

    public void setFineStatus(FineStatus fineStatus) {
        this.fineStatus = fineStatus;
    }

    public FineType fineType() {
        return fineType;
    }

    public void setFineType(FineType fineType) {
        this.fineType = fineType;
    }

    public UUID memberId() {
        return memberId;
    }

    public void setMemberId(UUID memberId) {
        this.memberId = memberId;
    }

    public UUID loanId() {
        return loanId;
    }

    public void setLoanId(UUID loanId) {
        this.loanId = loanId;
    }
}
