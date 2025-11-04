package com.turkcell.reservation_service.domain.port;

import com.turkcell.reservation_service.domain.model.Reservation;
import com.turkcell.reservation_service.domain.model.ReservationId;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);
    Optional<Reservation> findById(ReservationId reservationId);
    List<Reservation> findAll();
    void deleteById(ReservationId reservationId);
    void delete(Reservation reservation);
}
