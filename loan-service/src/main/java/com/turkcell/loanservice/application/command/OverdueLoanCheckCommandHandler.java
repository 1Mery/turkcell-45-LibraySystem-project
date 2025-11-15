package com.turkcell.loanservice.application.command;

import com.turkcell.loanservice.application.client.BookClient;
import com.turkcell.loanservice.application.client.UserClient;
import com.turkcell.loanservice.application.event.LoanEvent;
import com.turkcell.loanservice.application.event.LoanEventMapper;
import com.turkcell.loanservice.application.event.LoanEventPublisher;
import com.turkcell.loanservice.domain.model.Loan;
import com.turkcell.loanservice.domain.model.LoanStatus;
import com.turkcell.loanservice.domain.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class OverdueLoanCheckCommandHandler {

    private final LoanRepository repository;
    private final LoanEventPublisher publisher;
    private final UserClient user;
    private final BookClient book;

    public OverdueLoanCheckCommandHandler(LoanRepository repository, LoanEventPublisher publisher, UserClient user, BookClient book) {
        this.repository = repository;
        this.publisher = publisher;
        this.user = user;
        this.book = book;
    }

    public void loanCheck(){
        LocalDate today=LocalDate.now();

        List<Loan> activeLoans=repository.findAllByStatus(LoanStatus.ACTIVE);

        for (Loan loan:activeLoans){
            if (loan.getPeriod().isOverdue(today)){
                UUID userId = loan.getUserId().value();
                String email = user.getEmail(userId);
                String userName = user.getName(userId);

                String bookItemId = loan.getBookItemId().value().toString();
                String bookTitle = book.getBookTitle(bookItemId);

                LoanEvent event = LoanEventMapper.toOverdueEvent(
                        loan,
                        email,
                        userName,
                        bookTitle
                );

                publisher.publish(event);

                loan.markOverdue();

                repository.save(loan);
            }
        }
    }
}