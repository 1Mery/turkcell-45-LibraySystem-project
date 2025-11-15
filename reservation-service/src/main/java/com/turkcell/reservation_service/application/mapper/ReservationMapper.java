package com.turkcell.reservation_service.application.mapper;

import com.turkcell.reservation_service.application.command.CreateReservationCommand;
import com.turkcell.reservation_service.application.dto.CancelledReservationResponse;
import com.turkcell.reservation_service.application.dto.ReservationResponse;
import com.turkcell.reservation_service.domain.model.Reservation;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import com.turkcell.reservation_service.domain.model.MemberId;
import com.turkcell.reservation_service.domain.model.BookId;

@Component
public class ReservationMapper {

    public Reservation toDomain(CreateReservationCommand command) {
        return Reservation.create(
                OffsetDateTime.now(),
                new MemberId(command.memberId()),
                new BookId(command.bookId()));
    }

    public ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.id().value(),
                reservation.memberId().value(),
                reservation.bookId().value(),
                reservation.reservationDate(),
                reservation.status().name()
        );
    }

    public CancelledReservationResponse toCancelledResponse(Reservation reservation) {
        return new CancelledReservationResponse(
                reservation.id().value(),
                reservation.status().name()
        );
    }
}
