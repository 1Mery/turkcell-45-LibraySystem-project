package com.turkcell.reservation_service.application.query;

import com.turkcell.reservation_service.application.dto.ReservationResponse;
import com.turkcell.reservation_service.application.exception.ReservationNotFoundException;
import com.turkcell.reservation_service.application.mapper.ReservationMapper;
import com.turkcell.reservation_service.core.cqrs.QueryHandler;
import com.turkcell.reservation_service.domain.model.Reservation;
import com.turkcell.reservation_service.domain.model.ReservationId;
import com.turkcell.reservation_service.domain.port.ReservationRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class GetReservationByIdQueryHandler implements QueryHandler<GetReservationByIdQuery, ReservationResponse> {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public GetReservationByIdQueryHandler(ReservationRepository reservationRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    @Override
    public ReservationResponse handle(@Valid GetReservationByIdQuery query) {
        Reservation reservation = reservationRepository.findById(new ReservationId(query.id()))
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
        return reservationMapper.toResponse(reservation);

    }
}
