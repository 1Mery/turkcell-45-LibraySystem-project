package com.turkcell.loanservice.infrastructure.persistence.mapper;

import com.turkcell.loanservice.domain.model.*;
import com.turkcell.loanservice.infrastructure.persistence.entity.LoanEntity;
import org.springframework.stereotype.Component;

@Component
public class LoanEntityMapper {

    public LoanEntity toEntity(Loan loan){
        LoanEntity entity= new LoanEntity();
        entity.setId(loan.getId().value());
        entity.setUserId(loan.getUserId().value());
        entity.setBookItemId(loan.getBookItemId().value());
        entity.setLoanDate(loan.getPeriod().getLoanDate());
        entity.setDueDate(loan.getPeriod().getDueDate());
        entity.setReturnDate(loan.getReturnDate());
        entity.setStatus(loan.getStatus());
        return entity;
    }

    public Loan toDomain(LoanEntity entity){
        LoanPeriod period=new LoanPeriod(entity.getLoanDate(),entity.getDueDate());
        return Loan.rehydrate(
                new LoanId(entity.getId()),
                new UserId(entity.getUserId()),
                new BookItemId(entity.getBookItemId()),
                period,
                entity.getStatus(),
                entity.getReturnDate()
        );
    }


}
