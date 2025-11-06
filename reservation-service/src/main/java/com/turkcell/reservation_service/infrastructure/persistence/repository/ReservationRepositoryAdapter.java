package com.turkcell.reservation_service.infrastructure.persistence.repository;

import com.turkcell.reservation_service.domain.model.Reservation;
import com.turkcell.reservation_service.domain.model.ReservationId;
import com.turkcell.reservation_service.domain.port.ReservationRepository;
import com.turkcell.reservation_service.infrastructure.persistence.mapper.ReservationEntityMapper;
import com.turkcell.reservation_service.infrastructure.persistence.model.JpaReservationEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReservationRepositoryAdapter implements ReservationRepository {

    private final JpaReservationRepository reservationRepository;
    private final ReservationEntityMapper reservationMapper;

    public ReservationRepositoryAdapter(JpaReservationRepository reservationRepository, ReservationEntityMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    @Override
    public Reservation save(Reservation reservation) {
        JpaReservationEntity entity = reservationMapper.toEntity(reservation);
        return reservationMapper.toDomain(reservationRepository.save(entity));
    }

    @Override
    public Optional<Reservation> findById(ReservationId reservationId) {
        return reservationRepository
                .findById(reservationId.value())
                .map(reservationMapper::toDomain);
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository
                .findAll()
                .stream()
                .map(reservationMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(ReservationId reservationId) {
        reservationRepository.deleteById(reservationId.value());
    }

    @Override
    public void delete(Reservation reservation) {
        JpaReservationEntity entity = reservationMapper.toEntity(reservation);
        reservationRepository.delete(entity);
    }
}
