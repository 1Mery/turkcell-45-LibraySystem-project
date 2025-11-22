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
    private final UserClient userClient;
    private final BookClient bookClient;
    private final LoanEventMapper eventMapper;
    private final LoanEventPublisher publisher;

    public OverdueLoanCheckCommandHandler(LoanRepository repository,
                                          UserClient userClient,
                                          BookClient bookClient,
                                          LoanEventMapper eventMapper,
                                          LoanEventPublisher publisher) {
        this.repository = repository;
        this.userClient = userClient;
        this.bookClient = bookClient;
        this.eventMapper = eventMapper;
        this.publisher = publisher;
    }

    public void loanCheck() {
        LocalDate today = LocalDate.now();

        //Tüm aktif loanları al
        List<Loan> activeLoans = repository.findAllByStatus(LoanStatus.ACTIVE);

        for (Loan loan : activeLoans) {
            // Gecikmemişse atla
            if (!loan.isOverdueOn(today)) {
                continue;
            }

                UUID userId = loan.getUserId().value();
                UUID bookItemId = loan.getBookItemId().value();

                //Kullanıcı ve kitap bilgilerini Feign ile çek
                String email = userClient.getEmail(userId);
                String userName = userClient.getName(userId);
                String bookTitle = bookClient.getBookTitle(bookItemId);

                //Event'e map et
                LoanEvent event = eventMapper.toOverdueEvent(
                        loan,
                        email,
                        userName,
                        bookTitle,
                        today
                );

                //Kafka’ya gönder
                publisher.publish(event);

                //Loanı OVERDUE olarak kaydet
                loan.markOverdue();
                repository.save(loan);
            }
        }
    }