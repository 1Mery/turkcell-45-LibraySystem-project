package com.turkcell.reservation_service.infrastructure.messaging.relayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.reservation_service.infrastructure.messaging.event.ReservationCreatedIntegrationEvent;
import com.turkcell.reservation_service.infrastructure.messaging.outbox.OutboxMessage;
import com.turkcell.reservation_service.infrastructure.messaging.outbox.OutboxStatus;
import com.turkcell.reservation_service.infrastructure.messaging.repository.OutboxRepository;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

//Outbox Table'daki eventleri publish eder.
@Service
public class OutboxDomainEventRelayer {

    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;
    private final StreamBridge streamBridge;

    public OutboxDomainEventRelayer(OutboxRepository outboxRepository, ObjectMapper objectMapper, StreamBridge streamBridge) {
        this.outboxRepository = outboxRepository;
        this.objectMapper = objectMapper;
        this.streamBridge = streamBridge;
    }

    //async publish
    //retry yaparken duplicate oluşabilir.
    //At-least once delivery guarantees
    @Scheduled(fixedRate = 6000)
    public void publishPendingEvents() throws JsonProcessingException {

        List<OutboxMessage> pendingEvents = outboxRepository.findByStatusOrderByCreatedAtAsc(OutboxStatus.PENDING);
        for (OutboxMessage pendingEvent : pendingEvents) {

            ReservationCreatedIntegrationEvent event = objectMapper
                    .readValue(pendingEvent.payloadJson(), ReservationCreatedIntegrationEvent.class);

            //Message ile sarmalladık.
            Message<ReservationCreatedIntegrationEvent> message =
                    MessageBuilder
                            .withPayload(event)
                            .build();

            try{
                boolean isSent = streamBridge.send("reservationCreated-out-0", message);
                if(!isSent){
                    pendingEvent.setRetryCount(pendingEvent.retryCount() + 1);
                    if (pendingEvent.retryCount() > 5){
                        pendingEvent.setStatus(OutboxStatus.FAILED);
                    }
                }else {
                    pendingEvent.setStatus(OutboxStatus.SENT);
                }
                pendingEvent.setUpdatedAt(OffsetDateTime.now());
                outboxRepository.save(pendingEvent);
            }catch(Exception e){
                pendingEvent.setRetryCount(pendingEvent.retryCount() + 1);
                if(pendingEvent.retryCount() > 5){
                    pendingEvent.setStatus(OutboxStatus.FAILED);
                    pendingEvent.setUpdatedAt(OffsetDateTime.now());
                }
                outboxRepository.save(pendingEvent);

            }
        }

    }
}
