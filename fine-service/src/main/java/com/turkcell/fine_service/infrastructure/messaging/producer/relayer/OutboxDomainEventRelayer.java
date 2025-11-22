package com.turkcell.fine_service.infrastructure.messaging.producer.relayer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.fine_service.infrastructure.messaging.producer.event.FineCreatedEvent;
import com.turkcell.fine_service.infrastructure.persistence.entity.outbox.OutboxMessage;
import com.turkcell.fine_service.infrastructure.persistence.entity.outbox.OutboxStatus;
import com.turkcell.fine_service.infrastructure.persistence.repository.OutboxRepository;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

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

    @Scheduled(fixedRate = 60000)
    public void publishPendingEvents() throws JsonProcessingException {

        List<OutboxMessage> pendingEvents = outboxRepository.findByStatusOrderByCreatedAtAsc(OutboxStatus.PENDING);
        for (OutboxMessage pendingEvent : pendingEvents) {

            FineCreatedEvent event = objectMapper.readValue(pendingEvent.payloadJson(), FineCreatedEvent.class);

            Message<FineCreatedEvent> message = MessageBuilder.withPayload(event).build();

            try {
                boolean isSent = streamBridge.send("fineCreated-out-0", message);
                if (!isSent) {
                    pendingEvent.setRetryCount(pendingEvent.retryCount() + 1);
                    if (pendingEvent.retryCount() > 5){
                        pendingEvent.setStatus(OutboxStatus.FAILED);
                    }
                }else {
                    pendingEvent.setStatus(OutboxStatus.SENT);
                }
                pendingEvent.setUpdatedAt(OffsetDateTime.now());
                outboxRepository.save(pendingEvent);
            }catch (Exception e){
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
