package com.turkcell.reservation_service.infrastructure.persistence.mapper;

import com.turkcell.reservation_service.domain.model.*;
import com.turkcell.reservation_service.infrastructure.persistence.model.JpaReservationEntity;
import org.springframework.stereotype.Component;

@Component
public class ReservationEntityMapper {

    public JpaReservationEntity toEntity(Reservation reservation) {
        return new JpaReservationEntity(
                reservation.id().value(),
                reservation.reservationDate(),
                reservation.expireAt(),
                reservation.status().name(),
                reservation.memberId().value(),
                reservation.bookId().value()
        );
    }

    public Reservation toDomain(JpaReservationEntity entity) {
        return Reservation.rehydrate(
                new ReservationId(entity.id()),
                entity.reservationDate(),
                entity.expireAt(),
                ReservationStatus.valueOf(entity.status()),
                new MemberId(entity.memberId()),
                new BookId(entity.bookId())
        );
    }

}
