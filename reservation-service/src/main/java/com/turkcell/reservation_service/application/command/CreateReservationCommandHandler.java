package com.turkcell.reservation_service.application.command;

import com.turkcell.reservation_service.application.client.BookClient;
import com.turkcell.reservation_service.application.client.MemberClient;
import com.turkcell.reservation_service.application.dto.MemberResponse;
import com.turkcell.reservation_service.application.dto.ReservationResponse;
import com.turkcell.reservation_service.application.exception.MemberInactiveException;
import com.turkcell.reservation_service.application.mapper.ReservationMapper;
import com.turkcell.reservation_service.core.cqrs.CommandHandler;
import com.turkcell.reservation_service.domain.event.ReservationCreatedEvent;
import com.turkcell.reservation_service.domain.model.*;
import com.turkcell.reservation_service.domain.port.DomainEventsPublisher;
import com.turkcell.reservation_service.domain.port.ReservationRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class CreateReservationCommandHandler implements CommandHandler<CreateReservationCommand, ReservationResponse> {

    private final ReservationMapper reservationMapper;
    private final ReservationRepository reservationRepository;
    private final DomainEventsPublisher domainEventsPublisher;
    private final MemberClient memberClient;
    private final BookClient bookClient;

    public CreateReservationCommandHandler(ReservationMapper reservationMapper,
                                           ReservationRepository reservationRepository,
                                           DomainEventsPublisher domainEventsPublisher,
                                           MemberClient memberClient,
                                           BookClient bookClient) {
        this.reservationMapper = reservationMapper;
        this.reservationRepository = reservationRepository;
        this.domainEventsPublisher = domainEventsPublisher;
        this.memberClient = memberClient;
        this.bookClient = bookClient;
    }

    @Override
    @Transactional
    public ReservationResponse handle(CreateReservationCommand command) {
        //mevcut kopya sayısı 0'a eşitse reservasyon oluşturabilir.
        Integer availableCopies = bookClient.getAvailableCopies(command.bookId());
        if (availableCopies > 0) {
            throw new RuntimeException("Book has available copies, cannot reserve. you can borrow.");
        }

        String bookStatus = bookClient.getBookItemStatus(command.bookId());
        if (!bookStatus.equalsIgnoreCase("AVAILABLE")) {
            throw new RuntimeException("Book status is not AVAILABLE, cannot reserve.");
        }

        //üyenin rezervasyon oluşturabilmesi için üyelik statusü ACTIVE olmalıdır.
        MemberResponse responseFromMemberService = memberClient.getById(command.memberId());
        if (!"ACTIVE".equalsIgnoreCase(responseFromMemberService.memberStatus())){
            throw new MemberInactiveException("Member status is not active. Cannot create reservation.");
        }

        //aynı kitap için ACTIVE rezervasyon varsa yeni bir rez oluşturamaz.
        MemberId memberId = new MemberId(command.memberId());
        boolean activeReservation = reservationRepository.existsByMemberIdAndBookIdAndStatus(
                memberId,
                new BookId(command.bookId()),
                ReservationStatus.ACTIVE
        );

        if (activeReservation) {
            throw new IllegalStateException("Member already has an active reservation for this book.");
        }

        // Aynı kitap için PENDING rezervasyon var mı kontrol et, varsa yeni rez oluşturamaz.
        boolean pendingReservation = reservationRepository.existsByMemberIdAndBookIdAndStatus(
                memberId,
                new BookId(command.bookId()),
                ReservationStatus.PENDING
        );

        if (pendingReservation) {
            throw new IllegalStateException("Member already has a pending reservation for this book.");
        }

        Reservation reservation = reservationMapper.toDomain(command);
        reservationRepository.save(reservation);

        // Domain Event oluştur ve publish et
        // DomainEventsPublisherAdapter'ı this event'i alıp outboxa kaydedecek
        ReservationCreatedEvent event = new ReservationCreatedEvent(
                reservation.id(),
                reservation.reservationDate(),
                reservation.bookId(),
                reservation.memberId());

        domainEventsPublisher.publish(event);

        return reservationMapper.toResponse(reservation);
    }
}
