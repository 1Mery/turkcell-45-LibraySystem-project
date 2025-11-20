package com.turkcell.reservation_service.application.query;

import com.turkcell.reservation_service.application.dto.ReservationResponse;
import com.turkcell.reservation_service.application.mapper.ReservationMapper;
import com.turkcell.reservation_service.core.cqrs.QueryHandler;
import com.turkcell.reservation_service.domain.model.MemberId;
import com.turkcell.reservation_service.domain.port.ReservationRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetReservationsByMemberQueryHandler 
        implements QueryHandler<GetReservationsByMemberQuery, List<ReservationResponse>> {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public GetReservationsByMemberQueryHandler(
            ReservationRepository reservationRepository,
            ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    @Override
    public List<ReservationResponse> handle(GetReservationsByMemberQuery query) {
        return reservationRepository
                .findByMemberId(new MemberId(query.memberId()))
                .stream()
                .map(reservationMapper::toResponse)
                .toList();
    }
}


