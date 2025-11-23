package com.turkcell.loanservice.application.port;


import com.turkcell.loanservice.application.event.LoanEvent;

public interface LoanOutboxPort {
    void saveEvent(String eventType, LoanEvent event);
}

