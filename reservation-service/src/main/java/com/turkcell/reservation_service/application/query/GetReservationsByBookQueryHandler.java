package com.turkcell.reservation_service.application.query;

import com.turkcell.reservation_service.application.dto.ReservationResponse;
import com.turkcell.reservation_service.application.mapper.ReservationMapper;
import com.turkcell.reservation_service.core.cqrs.QueryHandler;
import com.turkcell.reservation_service.domain.model.BookId;
import com.turkcell.reservation_service.domain.model.Reservation;
import com.turkcell.reservation_service.domain.model.ReservationStatus;
import com.turkcell.reservation_service.domain.port.ReservationRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
public class GetReservationsByBookQueryHandler 
        implements QueryHandler<GetReservationsByBookQuery, List<ReservationResponse>> {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public GetReservationsByBookQueryHandler(
            ReservationRepository reservationRepository,
            ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    @Override
    public List<ReservationResponse> handle(GetReservationsByBookQuery query) {
        BookId bookId = new BookId(query.bookId());
        
        Stream<Reservation> reservations;
        
        if (query.status() != null && !query.status().isEmpty()) {
            try {
                ReservationStatus status = ReservationStatus.valueOf(query.status().toUpperCase());
                reservations = reservationRepository
                        .findByBookIdAndStatus(bookId, status)
                        .stream();
            } catch (IllegalArgumentException e) {
                // Invalid status, return all reservations for the book
                reservations = reservationRepository
                        .findByBookIdOrderByPriorityDescReservationDateAsc(bookId)
                        .stream();
            }
        } else {
            // Priority ve reservation date'e göre sıralı (queue management için)
            reservations = reservationRepository
                    .findByBookIdOrderByPriorityDescReservationDateAsc(bookId)
                    .stream();
        }
        
        return reservations
                .map(reservationMapper::toResponse)
                .toList();
    }
}


