package com.turkcell.loanservice.application.command.create;

import com.turkcell.loanservice.application.client.BookClient;
import com.turkcell.loanservice.application.client.UserClient;
import com.turkcell.loanservice.application.dto.CreateLoanResponse;
import com.turkcell.loanservice.application.event.LoanEvent;
import com.turkcell.loanservice.application.event.LoanEventMapper;
import com.turkcell.loanservice.application.event.LoanEventPublisher;
import com.turkcell.loanservice.application.exception.BookItemNotAvailableException;
import com.turkcell.loanservice.application.mapper.LoanMapper;
import com.turkcell.loanservice.domain.model.BookItemId;
import com.turkcell.loanservice.domain.model.Loan;
import com.turkcell.loanservice.domain.model.LoanPeriod;
import com.turkcell.loanservice.domain.model.UserId;
import com.turkcell.loanservice.domain.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class CreateLoanCommandHandler {

    private final LoanRepository loanRepository;
    private final LoanMapper mapper;
    private final BookClient bookClient;
    private final UserClient userClient;
    private final LoanEventMapper eventMapper;
    private final LoanEventPublisher eventPublisher;

    public CreateLoanCommandHandler(LoanRepository loanRepository,
                                    LoanMapper mapper,
                                    BookClient bookClient,
                                    UserClient userClient,
                                    LoanEventMapper eventMapper,
                                    LoanEventPublisher eventPublisher) {
        this.loanRepository = loanRepository;
        this.mapper = mapper;
        this.bookClient = bookClient;
        this.userClient = userClient;
        this.eventMapper = eventMapper;
        this.eventPublisher = eventPublisher;
    }

    public CreateLoanResponse createLoan(CreateLoanCommand command) {

        UUID userId = command.userId();
        UUID bookItemId = command.bookItemId();

        // Kitap uygun mu
        Boolean available = bookClient.isBookItemAvailable(bookItemId);
        if (available == null || !available) {
            throw new BookItemNotAvailableException(bookItemId);
        }

        //Domain loan oluştur
        LoanPeriod period = new LoanPeriod(command.loanDate(), command.dueDate());
        Loan loan = Loan.create(
                new UserId(userId),
                new BookItemId(bookItemId),
                period
        );

        //Loan kaydet
        loanRepository.save(loan);

        //Book-service bu item artık BORROWED
        bookClient.markBorrowed(bookItemId);

        // Bildirim için Feign ile user + book bilgilerini çek
        String email = userClient.getEmail(userId);
        String name = userClient.getName(userId);
        String bookTitle = bookClient.getBookTitle(bookItemId);

        // Event  Kafkaya gönder
        LoanEvent event = eventMapper.toCreatedEvent(
                loan,
                email,
                name,
                bookTitle
        );
        eventPublisher.publish(event);

        //Response dön
        return mapper.toCreateResponse(loan);
    }
}
