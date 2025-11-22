package com.turkcell.loanservice.application.command.returned;

import com.turkcell.loanservice.application.client.BookClient;
import com.turkcell.loanservice.application.client.UserClient;
import com.turkcell.loanservice.application.dto.ReturnLoanResponse;
import com.turkcell.loanservice.application.event.LoanEventMapper;
import com.turkcell.loanservice.application.event.LoanEventPublisher;
import com.turkcell.loanservice.application.event.LoanEvent;
import com.turkcell.loanservice.application.exception.LoanAlreadyReturnedException;
import com.turkcell.loanservice.application.exception.LoanNotFoundException;
import com.turkcell.loanservice.application.mapper.LoanMapper;
import com.turkcell.loanservice.domain.model.Loan;
import com.turkcell.loanservice.domain.model.LoanId;
import com.turkcell.loanservice.domain.model.LoanStatus;
import com.turkcell.loanservice.domain.repository.LoanRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class ReturnLoanCommandHandler {

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final UserClient userClient;
    private final BookClient bookClient;
    private final LoanEventMapper eventMapper;
    private final LoanEventPublisher publisher;

    public ReturnLoanCommandHandler(LoanRepository loanRepository,
                                    LoanMapper loanMapper,
                                    UserClient userClient,
                                    BookClient bookClient,
                                    LoanEventMapper eventMapper,
                                    LoanEventPublisher publisher) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
        this.userClient = userClient;
        this.bookClient = bookClient;
        this.eventMapper = eventMapper;
        this.publisher = publisher;
    }

    public ReturnLoanResponse returnLoan(ReturnLoanCommand command) {

        // Loan bul
        Loan loan = loanRepository.findById(new LoanId(command.loanId()))
                .orElseThrow(() -> new LoanNotFoundException(command.loanId()));

        if (loan.getStatus() == LoanStatus.RETURNED) {
            throw new LoanAlreadyReturnedException();
        }

        // Domainde iade et
        LocalDate returnDate = command.returnDate();
        loan.markReturned(returnDate);

        // Book-service bu item artık AVAILABLE
        UUID bookItemId = loan.getBookItemId().value();
        bookClient.markReturned(bookItemId);

        // Geç iade ise event gönder
        if (loan.isLateReturn()) {

            UUID userId = loan.getUserId().value();

            String email = userClient.getEmail(userId);
            String userName = userClient.getName(userId);
            String bookTitle = bookClient.getBookTitle(bookItemId);

            LoanEvent event = eventMapper.toLateReturnEvent(
                    loan,
                    email,
                    userName,
                    bookTitle,
                    returnDate
            );

            // Kafkaya gönder
            publisher.publish(event);
        }

        // Loanı kaydet
        loanRepository.save(loan);

        return loanMapper.toReturnResponse(loan);
    }
}
