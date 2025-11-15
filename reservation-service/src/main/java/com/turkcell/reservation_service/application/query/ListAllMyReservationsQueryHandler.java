package com.turkcell.reservation_service.application.query;

import com.turkcell.reservation_service.application.dto.ReservationResponse;
import com.turkcell.reservation_service.application.mapper.ReservationMapper;
import com.turkcell.reservation_service.core.cqrs.QueryHandler;
import com.turkcell.reservation_service.domain.port.ReservationRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListAllMyReservationsQueryHandler implements QueryHandler<ListAllMyReservationsQuery, List<ReservationResponse>> {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public ListAllMyReservationsQueryHandler(ReservationRepository reservationRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    @Override
    public List<ReservationResponse> handle(ListAllMyReservationsQuery query) {
        return reservationRepository
                .findAll()
                .stream()
                .map(reservationMapper::toResponse)
                .toList();
    }
}
