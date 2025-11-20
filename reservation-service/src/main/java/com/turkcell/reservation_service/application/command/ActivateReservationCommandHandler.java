package com.turkcell.reservation_service.application.command;

import com.turkcell.reservation_service.application.dto.ReservationResponse;
import com.turkcell.reservation_service.application.exception.ReservationNotFoundException;
import com.turkcell.reservation_service.application.mapper.ReservationMapper;
import com.turkcell.reservation_service.core.cqrs.CommandHandler;
import com.turkcell.reservation_service.domain.model.Reservation;
import com.turkcell.reservation_service.domain.model.ReservationId;
import com.turkcell.reservation_service.domain.model.ReservationStatus;
import com.turkcell.reservation_service.domain.port.ReservationRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//Manual Activation
@Component
public class ActivateReservationCommandHandler implements CommandHandler<ActivateReservationCommand, ReservationResponse> {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public ActivateReservationCommandHandler(
            ReservationRepository reservationRepository,
            ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    @Override
    @Transactional
    public ReservationResponse handle(ActivateReservationCommand command) {
        Reservation reservation = reservationRepository.findById(new ReservationId(command.reservationId()))
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
        
        if (reservation.status() != ReservationStatus.PENDING) {
            throw new IllegalStateException("Only PENDING reservations can be activated.");
        }
        
        reservation.updateReservationStatusAsActive();
        reservation.assignPickupWindow();
        reservation = reservationRepository.save(reservation);
        
        return reservationMapper.toResponse(reservation);
    }
}
