package com.turkcell.reservation_service.application.command;

import com.turkcell.reservation_service.application.dto.ReservationResponse;
import com.turkcell.reservation_service.application.mapper.ReservationMapper;
import com.turkcell.reservation_service.core.cqrs.CommandHandler;
import com.turkcell.reservation_service.domain.event.ReservationCreatedEvent;
import com.turkcell.reservation_service.domain.model.BookId;
import com.turkcell.reservation_service.domain.model.Reservation;
import com.turkcell.reservation_service.domain.model.ReservationId;
import com.turkcell.reservation_service.domain.port.DomainEventsPublisher;
import com.turkcell.reservation_service.domain.port.ReservationRepository;
import org.springframework.stereotype.Component;

@Component
public class CreateReservationCommandHandler implements CommandHandler<CreateReservationCommand, ReservationResponse> {

    private final ReservationMapper reservationMapper;
    private final ReservationRepository reservationRepository;
    private final DomainEventsPublisher domainEventPublisher;

    public CreateReservationCommandHandler(ReservationMapper reservationMapper, ReservationRepository reservationRepository, DomainEventsPublisher domainEventPublisher) {
        this.reservationMapper = reservationMapper;
        this.reservationRepository = reservationRepository;

        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public ReservationResponse handle(CreateReservationCommand command) {
        //TODO: eklenecek
        //business rule:
        //if: availableCopies > 0
        Reservation reservation = reservationMapper.toDomain(command);
        reservation = reservationRepository.save(reservation);

        ReservationCreatedEvent event = new ReservationCreatedEvent(
                new ReservationId(reservation.id().value()),
                reservation.reservationDate(),
                reservation.status(),
                new BookId(reservation.bookId().value()));
        domainEventPublisher.publish(event);

        return reservationMapper.toResponse(reservation);
    }
}
