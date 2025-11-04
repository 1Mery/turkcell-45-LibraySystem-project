package com.turkcell.reservation_service.domain.model;

import java.time.OffsetDateTime;

public class Reservation {

    private final ReservationId id;

    private OffsetDateTime reservationDate;
    private OffsetDateTime expireAt;
    private ReservationStatus status;

    private MemberId memberId;
    private BookId bookId;

    private Reservation(ReservationId id, OffsetDateTime reservationDate, OffsetDateTime expireAt, ReservationStatus status, MemberId memberId, BookId bookId) {
        this.id = id;
        this.reservationDate = reservationDate;
        this.expireAt = expireAt;
        this.status = status;
        this.memberId = memberId;
        this.bookId = bookId;
    }

    public static Reservation create(OffsetDateTime reservationDate, OffsetDateTime expireAt, ReservationStatus status, MemberId memberId, BookId bookId){
        validateReservationDate(reservationDate);
        validateExpireAt(expireAt, reservationDate);
        validateStatus(status);
        return new Reservation(
                ReservationId.generate(),
                reservationDate,
                expireAt,
                status,
                memberId,
                bookId
        );
    }

    public static Reservation rehydrate(ReservationId id, OffsetDateTime reservationDate, OffsetDateTime expireAt, ReservationStatus status, MemberId memberId, BookId bookId){
        return new Reservation(
                id,
                reservationDate,
                expireAt,
                status,
                memberId,
                bookId
        );
    }

    public void markAsCancelled(){
        this.status = ReservationStatus.CANCELLED;
    }

    public void markAsActive(){
        this.status = ReservationStatus.ACTIVE;
    }

    public void openPickupWindow(){

    }

    public static void validateReservationDate(OffsetDateTime reservationDate){
        if(reservationDate == null){
            throw new IllegalArgumentException("Reservation date cannot be null");
        }
        if (reservationDate.isBefore(OffsetDateTime.now())) {
            throw new IllegalArgumentException("Reservation date cannot be before now");
        }
    }

    public static void validateExpireAt(OffsetDateTime expireAt, OffsetDateTime reservationDate){
        if (expireAt == null){
            throw new IllegalArgumentException("Expire at cannot be null");
        }
        if (expireAt.isBefore(reservationDate)){
            throw new IllegalArgumentException("Expire at cannot be before reservation date " + reservationDate);
        }
    }

    public static void validateStatus(ReservationStatus status){
        if (status == null){
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
