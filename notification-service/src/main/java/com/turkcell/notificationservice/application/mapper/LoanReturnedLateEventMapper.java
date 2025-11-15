package com.turkcell.notificationservice.application.mapper;

import com.turkcell.notificationservice.application.event.LoanEvent;
import com.turkcell.notificationservice.application.usecase.SendLoanReturnedLateNotificationCommand;

public class LoanReturnedLateEventMapper {

    public static SendLoanReturnedLateNotificationCommand toCommand(LoanEvent event) {

        return new SendLoanReturnedLateNotificationCommand(
                event.userEmail(),
                event.userName(),
                event.bookTitle(),
                event.daysOverdue()
        );
    }
}
