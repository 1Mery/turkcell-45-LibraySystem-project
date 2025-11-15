package com.turkcell.loanservice.infrastructure.scheduler;

import com.turkcell.loanservice.application.command.OverdueLoanCheckCommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j  //loglama yapmamı kolaylaştıran logger ekler
@Component
public class LoanOverdueScheduler {

    private final OverdueLoanCheckCommandHandler handler;

    public LoanOverdueScheduler(OverdueLoanCheckCommandHandler handler) {
        this.handler = handler;
    }

    // Hergün 18:00 çalışır
    @Scheduled(cron = "0 0 18 * * *")
    public void runOverdueCheck() {
        log.info("Starting overdue loan check.");
        handler.loanCheck();
        log.info("Overdue loan check completed.");
    }
}
