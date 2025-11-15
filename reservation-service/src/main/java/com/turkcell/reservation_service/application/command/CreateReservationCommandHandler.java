package com.turkcell.reservation_service.application.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.reservation_service.application.client.MemberClient;
import com.turkcell.reservation_service.application.dto.MemberResponse;
import com.turkcell.reservation_service.application.dto.ReservationResponse;
import com.turkcell.reservation_service.application.exception.MemberInactiveException;
import com.turkcell.reservation_service.application.mapper.ReservationMapper;
import com.turkcell.reservation_service.core.cqrs.CommandHandler;
import com.turkcell.reservation_service.domain.event.ReservationCreatedEvent;
import com.turkcell.reservation_service.domain.model.BookId;
import com.turkcell.reservation_service.domain.model.MemberId;
import com.turkcell.reservation_service.domain.model.Reservation;
import com.turkcell.reservation_service.domain.model.ReservationId;
import com.turkcell.reservation_service.domain.port.ReservationRepository;
import com.turkcell.reservation_service.infrastructure.messaging.event.ReservationCreatedIntegrationEvent;
import com.turkcell.reservation_service.infrastructure.messaging.mapper.IntegrationEventMapper;
import com.turkcell.reservation_service.infrastructure.messaging.mapper.OutboxMapper;
import com.turkcell.reservation_service.infrastructure.messaging.outbox.OutboxMessage;
import com.turkcell.reservation_service.infrastructure.messaging.repository.OutboxRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;


@Component
public class CreateReservationCommandHandler implements CommandHandler<CreateReservationCommand, ReservationResponse> {

    private final ReservationMapper reservationMapper;
    private final ReservationRepository reservationRepository;
    private final ObjectMapper objectMapper;
    private final OutboxRepository outboxRepository;
    private final OutboxMapper outboxMapper;
    private final IntegrationEventMapper integrationEventMapper;
    private final MemberClient memberClient;

    public CreateReservationCommandHandler(ReservationMapper reservationMapper,
                                           ReservationRepository reservationRepository,
                                           ObjectMapper objectMapper,
                                           OutboxRepository outboxRepository, OutboxMapper outboxMapper, IntegrationEventMapper integrationEventMapper, MemberClient memberClient) {
        this.reservationMapper = reservationMapper;
        this.reservationRepository = reservationRepository;
        this.objectMapper = objectMapper;
        this.outboxRepository = outboxRepository;
        this.outboxMapper = outboxMapper;
        this.integrationEventMapper = integrationEventMapper;
        this.memberClient = memberClient;
    }

    @Override
    @Transactional
    public ReservationResponse handle(CreateReservationCommand command) {
        //TODO: business rule eklenecek
        //if: availableCopies == 0
        MemberResponse responseFromMemberService = memberClient.getById(command.memberId());
        if (!"ACTIVE".equalsIgnoreCase(responseFromMemberService.memberStatus())){
            throw new MemberInactiveException("Member status is not active. Cannot create reservation.");
        }

        Reservation reservation = reservationMapper.toDomain(command);
        reservationRepository.save(reservation);

        //Domain Event to Integration Event
        ReservationCreatedEvent event = new ReservationCreatedEvent(
                new ReservationId(reservation.id().value()),
                reservation.reservationDate(),
                new BookId(reservation.bookId().value()),
                new MemberId(reservation.memberId().value()));

        ReservationCreatedIntegrationEvent integrationEvent = integrationEventMapper.toIntegrationEvent(event);

        OutboxMessage outboxMessage = outboxMapper.toOutbox(integrationEvent);
        //from java object(integrationEvent) to serialize as json
        //serialize anında hata çıkabilir bu yüzden writeValueAsString methodu JsonProcessingException
        //hatası fırlatır ve bunu handle etmeni bekler.
        try {
            outboxMessage.setPayloadJson(objectMapper.writeValueAsString(integrationEvent));
        }catch (JsonProcessingException e){
            throw new RuntimeException("could not serialize integration event",e);
        }
        outboxRepository.save(outboxMessage);  //save event to outbox as json

        return reservationMapper.toResponse(reservation);
    }
}
