package com.turkcell.reservation_service.infrastructure.persistence.repository;

import com.turkcell.reservation_service.infrastructure.persistence.entity.JpaReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface JpaReservationRepository extends JpaRepository<JpaReservationEntity, UUID> {

    boolean existsByMemberIdAndBookIdAndStatus(UUID memberId, UUID bookId, String status);

    @Query("SELECT r FROM JpaReservationEntity r WHERE r.expireAt < :now " +
            "AND (r.status = 'ACTIVE' OR r.status = 'PENDING')")
    List<JpaReservationEntity> findExpiredReservations(@Param("now") OffsetDateTime now);

    List<JpaReservationEntity> findByBookIdAndStatus(UUID bookId, String status);

    List<JpaReservationEntity> findByMemberId(UUID memberId);

    long countByMemberIdAndStatus(UUID memberId, String status);

    List<JpaReservationEntity> findByBookIdOrderByReservationDateAsc(UUID bookId);
}
