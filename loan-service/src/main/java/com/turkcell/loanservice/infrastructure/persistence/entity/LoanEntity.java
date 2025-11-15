package com.turkcell.loanservice.infrastructure.persistence.entity;

import com.turkcell.loanservice.domain.model.LoanStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name ="loans")
@Getter
@Setter
public class LoanEntity {

    @Id
    private UUID id;

    private UUID userId;
    private UUID bookItemId;

    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    //getter ve setter'lar için lombok kullandım
}
