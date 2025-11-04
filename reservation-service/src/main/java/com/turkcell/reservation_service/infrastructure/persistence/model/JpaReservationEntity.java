package com.turkcell.reservation_service.infrastructure.persistence.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "reservations")
public class JpaReservationEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "reservation_date")
    private OffsetDateTime reservationDate;
    @Column(name = "expire_at")
    private OffsetDateTime expireAt;
    @Column(name = "status")
    private String status;

    @Column(name = "member_id")
    private UUID memberId;
    @Column(name = "book_id")
    private UUID bookId;

    public JpaReservationEntity() {
    }

    public JpaReservationEntity(UUID id, OffsetDateTime reservationDate, OffsetDateTime expireAt, String status, UUID memberId, UUID bookId) {
        this.id = id;
        this.reservationDate = reservationDate;
        this.expireAt = expireAt;
        this.status = status;
        this.memberId = memberId;
        this.bookId = bookId;
    }


    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OffsetDateTime reservationDate() {
        return reservationDate;
    }

    public void setReservationDate(OffsetDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public OffsetDateTime expireAt() {
        return expireAt;
    }

    public void setExpireAt(OffsetDateTime expireAt) {
        this.expireAt = expireAt;
    }

    public String status() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID memberId() {
        return memberId;
    }

    public void setMemberId(UUID memberId) {
        this.memberId = memberId;
    }

    public UUID bookId() {
        return bookId;
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }
}
