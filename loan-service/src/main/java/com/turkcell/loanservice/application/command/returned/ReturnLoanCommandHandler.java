package com.turkcell.loanservice.application.command.returned;

import com.turkcell.loanservice.application.client.BookClient;
import com.turkcell.loanservice.application.client.UserClient;
import com.turkcell.loanservice.application.dto.ReturnLoanResponse;
import com.turkcell.loanservice.application.event.LoanEventMapper;
import com.turkcell.loanservice.application.event.LoanEventPublisher;
import com.turkcell.loanservice.application.event.LoanEvent;
import com.turkcell.loanservice.application.mapper.LoanMapper;
import com.turkcell.loanservice.domain.model.Loan;
import com.turkcell.loanservice.domain.model.LoanId;
import com.turkcell.loanservice.domain.repository.LoanRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReturnLoanCommandHandler {

    private final LoanRepository loanRepository;
    private final LoanEventMapper mapper;
    private final UserClient userClient;
    private final BookClient bookClient;
    private final LoanMapper loanMapper;
    private final LoanEventPublisher publisher;

    public ReturnLoanCommandHandler(LoanRepository loanRepository, LoanEventMapper mapper, UserClient userClient, BookClient bookClient, LoanMapper loanMapper, LoanEventPublisher publisher) {
        this.loanRepository = loanRepository;
        this.mapper = mapper;
        this.userClient = userClient;
        this.bookClient = bookClient;
        this.loanMapper = loanMapper;
        this.publisher = publisher;
    }

    public ReturnLoanResponse returnLoan(ReturnLoanCommand command) {
        //Loan bul
        Loan loan = loanRepository.findById(new LoanId(command.loanId()))
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));

        LocalDate today = command.returnDate();

        //Domain davranışı kitabı iade et
        loan.markReturned(today);

        //Overdue kontrolü
        boolean isLate = loan.getPeriod().isOverdue(today);

        if (isLate) {

            //User name ve email Feign ile al
            String userEmail = userClient.getEmail(loan.getUserId().value());
            String userName=userClient.getName(loan.getUserId().value());

            // Book title Feign ile al
            String bookTitle = bookClient.getBookTitle(loan.getBookItemId().value().toString());

            //event oluştur
            LoanEvent event = mapper.toReturnedLateEvent(
                    loan,
                    userEmail,
                    userName,
                    bookTitle,
                    command.returnDate()
            );

            //Kafka’ya gönder
            publisher.publish(event);
        }

        //Loan kaydet
        loanRepository.save(loan);

        return loanMapper.toReturnResponse(loan);
    }
}
