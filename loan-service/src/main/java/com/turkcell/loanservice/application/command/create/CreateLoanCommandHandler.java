package com.turkcell.loanservice.application.command.create;

import com.turkcell.loanservice.application.client.BookClient;
import com.turkcell.loanservice.application.client.UserClient;
import com.turkcell.loanservice.application.dto.CreateLoanResponse;
import com.turkcell.loanservice.application.event.LoanEvent;
import com.turkcell.loanservice.application.event.LoanEventMapper;
import com.turkcell.loanservice.application.event.LoanEventPublisher;
import com.turkcell.loanservice.application.event.LoanOutboxService;
import com.turkcell.loanservice.application.exception.BookItemNotAvailableException;
import com.turkcell.loanservice.application.mapper.LoanMapper;
import com.turkcell.loanservice.domain.model.*;
import com.turkcell.loanservice.domain.repository.LoanRepository;
import com.turkcell.loanservice.infrastructure.idompotency.IdempotentRequestEntity;
import com.turkcell.loanservice.infrastructure.idompotency.IdempotentRequestRepository;
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
    private final IdempotentRequestRepository idempotentRequestRepository;
    private final LoanOutboxService outboxService;


    public CreateLoanCommandHandler(LoanRepository loanRepository, LoanMapper mapper, BookClient bookClient, UserClient userClient, LoanEventMapper eventMapper, LoanEventPublisher eventPublisher, IdempotentRequestRepository idempotentRequestRepository, LoanOutboxService outboxService) {
        this.loanRepository = loanRepository;
        this.mapper = mapper;
        this.bookClient = bookClient;
        this.userClient = userClient;
        this.eventMapper = eventMapper;
        this.eventPublisher = eventPublisher;
        this.idempotentRequestRepository = idempotentRequestRepository;
        this.outboxService = outboxService;
    }

    public CreateLoanResponse createLoan(CreateLoanCommand command) {

        UUID userId = command.userId();
        UUID bookItemId = command.bookItemId();

        // Idempotency Bu request daha önce işlenmiş mi
        if (command.requestId() != null) {
            var existing = idempotentRequestRepository.findById(command.requestId());
            if (existing.isPresent()) {
                // Daha önce aynı request ile bir loan oluşturulmuş
                UUID existingLoanId = existing.get().getResourceId();
                Loan existingLoan = loanRepository.findById(new LoanId(existingLoanId))
                        .orElseThrow(); // burada istersen LoanNotFoundException kullan
                return mapper.toCreateResponse(existingLoan);
            }
        }

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
        bookClient.markLoaned(bookItemId);

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
        outboxService.saveEvent("LOAN_CREATED", event);

        // IdempotentRequest kaydet artık bu requestId işlendi
        if (command.requestId() != null) {
            IdempotentRequestEntity entity = new IdempotentRequestEntity();
            entity.setRequestId(command.requestId());
            entity.setOperation("CREATE_LOAN");
            entity.setResourceId(loan.getId().value());
            entity.setCreatedAt(Instant.now());
            idempotentRequestRepository.save(entity);
        }

        //Response dön
        return mapper.toCreateResponse(loan);
    }
}
