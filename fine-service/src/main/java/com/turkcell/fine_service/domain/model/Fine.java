package com.turkcell.fine_service.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

public class Fine {

    private final FineId id;

    private BigDecimal fineAmount;
    private OffsetDateTime fineDate;

    private FineStatus status;
    private FineType fineType;

    private MemberId memberId;
    private LoanId loanId;

    private Fine(FineId id, BigDecimal fineAmount, OffsetDateTime fineDate,
                 FineStatus status, FineType fineType, MemberId memberId, LoanId loanId) {
        inputValidations(fineAmount, fineDate);
        this.id = id;
        this.fineAmount = fineAmount;
        this.fineDate = fineDate;
        this.status = status != null ? status : FineStatus.getDefault();
        this.fineType = fineType;
        this.memberId = memberId;
        this.loanId = loanId;
    }

    public static Fine create(BigDecimal fineAmount, OffsetDateTime fineDate, FineStatus status,
                              FineType fineType, MemberId memberId, LoanId loanId) {
        Objects.requireNonNull(memberId, "Member Id cannot be null");
        Objects.requireNonNull(loanId, "Loan Id cannot be null");
        return new Fine(
                FineId.generate(),
                fineAmount,
                fineDate,
                status != null ? status : FineStatus.getDefault(),
                fineType,
                memberId,
                loanId
        );
    }

    public static Fine rehydrate(FineId id, BigDecimal fineAmount, OffsetDateTime fineDate,
                                 FineStatus status, FineType fineType, MemberId memberId, LoanId loanId) {
        return new Fine(id, fineAmount, fineDate, status, fineType, memberId, loanId);
    }


    /**
     * Ceza miktarını hesaplar.
     * Gecikme cezası (LATE): (returnDate - dueDate) gün * dailyRate
     * Kayıp/Hasar cezası (LOST/DAMAGED): kitap bedeli
     *
     * @param daysOverdue Gecikme gün sayısı (LATE tipi için gerekli)
     */
    public void calculateFineAmount(int daysOverdue) {

        if (daysOverdue < 0) {
            throw new IllegalArgumentException("Days overdue cannot be negative");
        }

        BigDecimal dailyRate = BigDecimal.valueOf(5.0);
        // Gecikme cezası: gün sayısı * günlük oran

        BigDecimal calculatedAmount = BigDecimal.valueOf(daysOverdue)
                .multiply(dailyRate);

        // Hesaplanan miktarı güncelle
        this.fineAmount = calculatedAmount;
    }


    public void markAsPaid() {
        if (this.status == FineStatus.PAID) {
            throw new IllegalStateException("Fine is already marked as paid");
        }
        this.status = FineStatus.PAID;
    }

    public static void inputValidations(BigDecimal fineAmount, OffsetDateTime fineDate){
        if (fineAmount == null){
            throw new IllegalArgumentException("Fine amount cannot be null");
        }
        if (fineAmount.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Fine amount cannot be negative");
        }
        if (fineDate == null){
            throw new IllegalArgumentException("Fine date cannot be null");
        }
    }

    //getters
    public FineId id() {
        return id;
    }

    public BigDecimal fineAmount() {
        return fineAmount;
    }

    public OffsetDateTime fineDate() {
        return fineDate;
    }

    public FineStatus status() {
        return status;
    }

    public FineType fineType() {
        return fineType;
    }

    public MemberId memberId() {
        return memberId;
    }

    public LoanId loanId() {
        return loanId;
    }
}
