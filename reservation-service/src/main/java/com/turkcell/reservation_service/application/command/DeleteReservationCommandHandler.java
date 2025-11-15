package com.turkcell.reservation_service.application.command;

import com.turkcell.reservation_service.application.dto.DeletedReservationResponse;
import com.turkcell.reservation_service.application.exception.ReservationNotFoundException;
import com.turkcell.reservation_service.core.cqrs.CommandHandler;
import com.turkcell.reservation_service.domain.model.Reservation;
import com.turkcell.reservation_service.domain.model.ReservationId;
import com.turkcell.reservation_service.domain.port.ReservationRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteReservationCommandHandler implements CommandHandler<DeleteReservationCommand, DeletedReservationResponse> {

    private final ReservationRepository reservationRepository;

    public DeleteReservationCommandHandler(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public DeletedReservationResponse handle(DeleteReservationCommand command) {
        Reservation reservation = reservationRepository.findById(new ReservationId(command.id()))
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
        reservationRepository.delete(reservation);

        return new DeletedReservationResponse(reservation.id().value());
    }
}
