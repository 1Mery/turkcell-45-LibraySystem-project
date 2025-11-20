package com.turkcell.reservation_service.domain.model;

import java.time.OffsetDateTime;

public class Reservation {

    private final ReservationId id;

    private OffsetDateTime reservationDate;
    private OffsetDateTime expireAt;
    private ReservationStatus status;

    private MemberId memberId;
    private BookId bookId;

    private String cancellationReason; // İptal sebebi
    private OffsetDateTime lastModifiedAt; // Son değişiklik zamanı

    private Reservation(ReservationId id, OffsetDateTime reservationDate, OffsetDateTime expireAt,
            ReservationStatus status, MemberId memberId, BookId bookId, String cancellationReason, OffsetDateTime lastModifiedAt) {
        validateInputs(reservationDate, expireAt, status);
        this.id = id;
        this.reservationDate = reservationDate;
        this.expireAt = expireAt;
        this.status = status != null ? status : ReservationStatus.getDefault();
        this.memberId = memberId;
        this.bookId = bookId;
        this.cancellationReason = cancellationReason;
        this.lastModifiedAt = lastModifiedAt != null ? lastModifiedAt : OffsetDateTime.now();
    }

    public static Reservation create(OffsetDateTime reservationDate, MemberId memberId, BookId bookId) {
        // expireAt ilk atamada null olacak.
        return new Reservation(
                ReservationId.generate(),
                reservationDate,
                null, // expireAt is null on creation.
                ReservationStatus.getDefault(),
                memberId,
                bookId,
                null,
                OffsetDateTime.now());
    }

    public static Reservation rehydrate(ReservationId id, OffsetDateTime reservationDate, OffsetDateTime expireAt,
            ReservationStatus status, MemberId memberId, BookId bookId, String cancellationReason, OffsetDateTime lastModifiedAt) {
        return new Reservation(
                id,
                reservationDate,
                expireAt,
                status,
                memberId,
                bookId,
                cancellationReason,
                lastModifiedAt);
    }

    public void updateReservationStatusAsCancelled(String reason) {
        this.status = ReservationStatus.CANCELLED;
        this.cancellationReason = reason;
        this.lastModifiedAt = OffsetDateTime.now();
    }


    public void updateReservationStatusAsActive() {
        this.status = ReservationStatus.ACTIVE;
        this.lastModifiedAt = OffsetDateTime.now();
    }

    
    public void markAsFulfilled() {
        this.status = ReservationStatus.FULFILLED;
        this.lastModifiedAt = OffsetDateTime.now();
    }
    
    public boolean isExpired() {
        return this.expireAt != null && OffsetDateTime.now().isAfter(this.expireAt) 
                && (this.status == ReservationStatus.ACTIVE || this.status == ReservationStatus.PENDING);
    }
    
    public boolean isActive() {
        return this.status == ReservationStatus.ACTIVE || this.status == ReservationStatus.PENDING;
    }

    public void assignPickupWindow() {
        if(this.status != ReservationStatus.ACTIVE) {
            throw new IllegalStateException("Reservation can be assigned a pickup window only when status is ACTIVE");
        }
        if (this.expireAt != null) {
            throw new IllegalStateException("Pickup Window already assigned");
        }
        this.expireAt = OffsetDateTime.now().plusHours(24);
    }

    public static void validateInputs(OffsetDateTime reservationDate, OffsetDateTime expireAt, ReservationStatus status) {
        if (reservationDate == null) {
            throw new IllegalArgumentException("Reservation date cannot be null");
        }
        if (reservationDate.isBefore(OffsetDateTime.now())) {
            throw new IllegalArgumentException("Reservation date cannot be before now");
        }
        if (expireAt == null) {
            return; // null is allowed on creation or before pickup window
        }
        if (expireAt.isBefore(reservationDate)) {
            throw new IllegalArgumentException("Expire at cannot be before reservation date " + reservationDate);
        }
        if (status == null) {
            throw new IllegalArgumentException("ReservationStatus cannot be null");
        }
    }

    public ReservationId id() {
        return id;
    }

    public OffsetDateTime reservationDate() {
        return reservationDate;
    }

    public OffsetDateTime expireAt() {
        return expireAt;
    }

    public ReservationStatus status() {
        return status;
    }

    public MemberId memberId() {
        return memberId;
    }

    public BookId bookId() {
        return bookId;
    }
    
    public String cancellationReason() {
        return cancellationReason;
    }
    
    public OffsetDateTime lastModifiedAt() {
        return lastModifiedAt;
    }
}
