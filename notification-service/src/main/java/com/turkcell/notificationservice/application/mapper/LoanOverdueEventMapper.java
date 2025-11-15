package com.turkcell.notificationservice.application.mapper;

import com.turkcell.notificationservice.application.event.LoanEvent;
import com.turkcell.notificationservice.application.usecase.SendLoanOverdueNotificationCommand;

public class LoanOverdueEventMapper {

    public static SendLoanOverdueNotificationCommand toCommand(LoanEvent event) {

        return new SendLoanOverdueNotificationCommand(
                event.userEmail(),
                event.userName(),
                event.bookTitle(),
                event.dueDate(),
                event.daysOverdue()
        );
    }
}