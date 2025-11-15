package com.turkcell.loanservice.application.command.create;

import com.turkcell.loanservice.application.dto.CreateLoanResponse;
import com.turkcell.loanservice.application.mapper.LoanMapper;
import com.turkcell.loanservice.domain.model.BookItemId;
import com.turkcell.loanservice.domain.model.Loan;
import com.turkcell.loanservice.domain.model.LoanPeriod;
import com.turkcell.loanservice.domain.model.UserId;
import com.turkcell.loanservice.domain.repository.LoanRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateLoanCommandHandler {

    private final LoanRepository loanRepository;
    private final LoanMapper mapper;

    public CreateLoanCommandHandler(LoanRepository loanRepository, LoanMapper mapper) {
        this.loanRepository = loanRepository;
        this.mapper = mapper;
    }


    public CreateLoanResponse createLoan(CreateLoanCommand command) {
        //Domain VO oluştur
        LoanPeriod period = new LoanPeriod(command.loanDate(), command.dueDate());

        //Domain davranışını çağır
        Loan loan = Loan.create(
                new UserId(command.userId()),
                new BookItemId(command.bookItemId()),
                period
        );

        //Kaydet
        loanRepository.save(loan);


        //Response dön
        return mapper.toCreateResponse(loan);
    }
}
