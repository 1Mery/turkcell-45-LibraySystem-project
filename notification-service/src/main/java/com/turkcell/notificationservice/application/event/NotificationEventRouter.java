package com.turkcell.notificationservice.application.event;

import com.turkcell.notificationservice.application.mapper.LoanOverdueEventMapper;
import com.turkcell.notificationservice.application.mapper.LoanReturnedLateEventMapper;
import com.turkcell.notificationservice.application.usecase.SendLoanOverdueNotificationHandler;
import com.turkcell.notificationservice.application.usecase.SendLoanReturnedLateNotificationHandler;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventRouter {

    private final SendLoanOverdueNotificationHandler overdueHandler;
    private final SendLoanReturnedLateNotificationHandler returnedLateHandler;

    public NotificationEventRouter(
            SendLoanOverdueNotificationHandler overdueHandler,
            SendLoanReturnedLateNotificationHandler returnedLateHandler
    ) {
        this.overdueHandler = overdueHandler;
        this.returnedLateHandler = returnedLateHandler;
    }

    public void route(LoanEvent event) {

        if(event.reason().equals("LOAN_OVERDUE")) {

            overdueHandler.loanNotification(
                    LoanOverdueEventMapper.toCommand(event)
            );
        }
        else if(event.reason().equals("LOAN_RETURNED_LATE")) {

            returnedLateHandler.loanReturn(
                    LoanReturnedLateEventMapper.toCommand(event)
            );
        }
    }
}
