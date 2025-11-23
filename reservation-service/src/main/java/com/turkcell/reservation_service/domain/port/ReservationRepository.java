package com.turkcell.reservation_service.domain.port;

import com.turkcell.reservation_service.domain.model.*;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Optional<Reservation> findById(ReservationId reservationId);

    List<Reservation> findAll();

    void deleteById(ReservationId reservationId);

    void delete(Reservation reservation);

    boolean existsByMemberIdAndBookIdAndStatus(MemberId memberId, BookId bookId, ReservationStatus reservationStatus);

    List<Reservation> findExpiredReservations();

    List<Reservation> findByBookIdAndStatus(BookId bookId, ReservationStatus status);

    List<Reservation> findByMemberId(MemberId memberId);

    long countByMemberIdAndStatus(MemberId memberId, ReservationStatus status);

    List<Reservation> findByBookIdOrderByReservationDateAsc(BookId bookId);
}
