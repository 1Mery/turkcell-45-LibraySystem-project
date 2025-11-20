package com.turkcell.reservation_service.infrastructure.messaging.producer.adapter;

import com.turkcell.reservation_service.domain.event.ReservationCancelledEvent;
import com.turkcell.reservation_service.domain.event.ReservationCreatedEvent;
import com.turkcell.reservation_service.domain.event.ReservationExpiredEvent;
import com.turkcell.reservation_service.domain.port.DomainEventsPublisher;
import com.turkcell.reservation_service.infrastructure.messaging.producer.event.ReservationCancelledIntegrationEvent;
import com.turkcell.reservation_service.infrastructure.messaging.producer.event.ReservationCreatedIntegrationEvent;
import com.turkcell.reservation_service.infrastructure.persistence.entity.OutboxMessage;
import com.turkcell.reservation_service.infrastructure.persistence.mapper.IntegrationEventMapper;
import com.turkcell.reservation_service.infrastructure.persistence.mapper.OutboxMapper;
import com.turkcell.reservation_service.infrastructure.persistence.repository.OutboxRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DomainEventsPublisherAdapter implements DomainEventsPublisher {

    private final OutboxRepository outboxRepository;
    private final IntegrationEventMapper integrationEventMapper;
    private final OutboxMapper outboxMapper;

    public DomainEventsPublisherAdapter(OutboxRepository outboxRepository,
                                        IntegrationEventMapper integrationEventMapper,
                                        OutboxMapper outboxMapper) {
        this.outboxRepository = outboxRepository;
        this.integrationEventMapper = integrationEventMapper;
        this.outboxMapper = outboxMapper;
    }

    @Override
    public void publish(ReservationCreatedEvent reservationCreatedEvent) {

        ReservationCreatedIntegrationEvent integrationEvent =
                integrationEventMapper.toIntegrationEvent(reservationCreatedEvent);

        OutboxMessage outboxMessage = outboxMapper.toOutbox(integrationEvent);
        outboxRepository.save(outboxMessage);
    }

    @Override
    public void publish(ReservationCancelledEvent reservationCancelledEvent) {

        ReservationCancelledIntegrationEvent integrationEvent =
                integrationEventMapper.toIntegrationEvent(reservationCancelledEvent);

        OutboxMessage outboxMessage = new OutboxMessage();
        outboxMessage.setAggregateId(integrationEvent.reservationId());
        outboxMessage.setAggregateType("Reservation");
        outboxMessage.setEventId(UUID.randomUUID());
        outboxMessage.setEventType("ReservationCancelledEvent");
        //outboxMessage.setPayloadJson();
        outboxRepository.save(outboxMessage);

    }

    @Override
    public void publish(ReservationExpiredEvent reservationExpiredEvent) {
        // TODO: ReservationExpiredIntegrationEvent oluşturulduğunda implement edilecek
    }
}
