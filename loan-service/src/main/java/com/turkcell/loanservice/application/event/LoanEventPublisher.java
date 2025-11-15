package com.turkcell.loanservice.application.event;

import org.springframework.stereotype.Service;
import org.springframework.cloud.stream.function.StreamBridge;


@Service
public class LoanEventPublisher {

    private final StreamBridge streamBridge;

    public LoanEventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publish(LoanEvent event) {
        streamBridge.send("loan-events-out-0", event);  //topic adına dikkat et
    }
}
