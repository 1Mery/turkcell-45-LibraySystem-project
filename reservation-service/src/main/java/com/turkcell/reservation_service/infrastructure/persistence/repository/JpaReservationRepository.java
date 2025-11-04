package com.turkcell.reservation_service.infrastructure.persistence.repository;

import com.turkcell.reservation_service.infrastructure.persistence.model.JpaReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaReservationRepository extends JpaRepository<JpaReservationEntity, UUID> {
}
