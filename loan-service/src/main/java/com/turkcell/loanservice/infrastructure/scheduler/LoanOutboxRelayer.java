package com.turkcell.loanservice.infrastructure.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.loanservice.application.event.LoanEvent;
import com.turkcell.loanservice.application.event.LoanEventPublisher;
import com.turkcell.loanservice.infrastructure.persistence.outbox.LoanOutboxEntity;
import com.turkcell.loanservice.infrastructure.persistence.outbox.OutboxStatus;
import com.turkcell.loanservice.infrastructure.persistence.outbox.SpringDataLoanOutboxRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

// Bu sınıf belli aralıklarla Outbox tablosunu kontrol edip
// NEW durumundaki event'leri Kafka'ya gönderen relayer sınıfı.
@Slf4j
@Component
public class LoanOutboxRelayer {

    private final SpringDataLoanOutboxRepository repository;
    private final LoanEventPublisher publisher;
    private final ObjectMapper objectMapper;

    public LoanOutboxRelayer(SpringDataLoanOutboxRepository repository,
                             LoanEventPublisher publisher,
                             ObjectMapper objectMapper) {
        this.repository = repository;
        this.publisher = publisher;
        this.objectMapper = objectMapper;
    }

    // her 10 saniyede bir pending eventleri oku
    @Scheduled(fixedDelay = 10_000)
    public void relay() {
        List<LoanOutboxEntity> pending =
                repository.findTop50ByStatusOrderByCreatedAtAsc(OutboxStatus.NEW);

        for (LoanOutboxEntity entity : pending) {
            try {
                LoanEvent event = objectMapper.readValue(
                        entity.getPayload(),
                        LoanEvent.class
                );

                publisher.publish(event);

                entity.setStatus(OutboxStatus.SENT);
                entity.setLastAttemptAt(Instant.now());
                repository.save(entity);

            } catch (Exception e) {
                log.error("Outbox relay failed for id {}", entity.getId(), e);
                entity.setStatus(OutboxStatus.FAILED);
                entity.setLastAttemptAt(Instant.now());
                repository.save(entity);
            }
        }
    }
}
