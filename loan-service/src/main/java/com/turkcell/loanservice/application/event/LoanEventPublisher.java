package com.turkcell.loanservice.application.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.cloud.stream.function.StreamBridge;

@Slf4j
@Service
public class LoanEventPublisher {

    private final StreamBridge streamBridge;

    public LoanEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publish(LoanEvent event) {
        boolean sent = streamBridge.send("loan-events-out-0", event);

        if (sent) {
            log.info("LoanEvent sent Kafka → {}", event);
        } else {
            log.error("LoanEvent can not sent Kafka → {}", event);
        }
    }
}
