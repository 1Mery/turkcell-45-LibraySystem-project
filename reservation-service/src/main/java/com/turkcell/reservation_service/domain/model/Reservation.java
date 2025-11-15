package com.turkcell.reservation_service.domain.model;

import com.turkcell.reservation_service.domain.event.ReservationCancelledEvent;

import java.time.OffsetDateTime;

public class Reservation {

    private final ReservationId id;

    private OffsetDateTime reservationDate;
    private OffsetDateTime expireAt;
    private ReservationStatus status;

    private MemberId memberId;
    private BookId bookId;

    private Reservation(ReservationId id, OffsetDateTime reservationDate, OffsetDateTime expireAt,
            ReservationStatus status, MemberId memberId, BookId bookId) {
        validateInputs(reservationDate, expireAt, status);
        this.id = id;
        this.reservationDate = reservationDate;
        this.expireAt = expireAt;
        this.status = status != null ? status : ReservationStatus.getDefault();
        this.memberId = memberId;
        this.bookId = bookId;
    }

    public static Reservation create(OffsetDateTime reservationDate, MemberId memberId, BookId bookId) {
        // expireAt ilk atamada null olacak.
        return new Reservation(
                ReservationId.generate(),
                reservationDate,
                null, // expireAt is null on creation.
                ReservationStatus.getDefault(),
                memberId,
                bookId);
    }

    public static Reservation rehydrate(ReservationId id, OffsetDateTime reservationDate, OffsetDateTime expireAt,
            ReservationStatus status, MemberId memberId, BookId bookId) {
        return new Reservation(
                id,
                reservationDate,
                expireAt,
                status,
                memberId,
                bookId);
    }

    public void updateReservationStatusAsCancelled() {
        this.status = ReservationStatus.CANCELLED;
    }

    public void updateReservationStatusAsActive() {
        this.status = ReservationStatus.ACTIVE;
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
}
