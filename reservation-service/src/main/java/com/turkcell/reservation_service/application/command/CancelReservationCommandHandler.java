package com.turkcell.reservation_service.application.command;

import com.turkcell.reservation_service.application.dto.CancelledReservationResponse;
import com.turkcell.reservation_service.application.exception.ReservationNotFoundException;
import com.turkcell.reservation_service.application.mapper.ReservationMapper;
import com.turkcell.reservation_service.core.cqrs.CommandHandler;
import com.turkcell.reservation_service.domain.model.Reservation;
import com.turkcell.reservation_service.domain.model.ReservationId;
import com.turkcell.reservation_service.domain.port.ReservationRepository;
import org.springframework.stereotype.Component;

@Component
public class CancelReservationCommandHandler implements CommandHandler<CancelReservationCommand, CancelledReservationResponse> {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public CancelReservationCommandHandler(ReservationRepository reservationRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    @Override
    public CancelledReservationResponse handle(CancelReservationCommand command) {

        Reservation reservation = reservationRepository.findById(new ReservationId(command.reservationId()))
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
        reservation.updateReservationStatusAsCancelled();
        reservation = reservationRepository.save(reservation);

        return reservationMapper.toCancelledResponse(reservation);

    }
}
