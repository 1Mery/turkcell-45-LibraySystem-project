package com.turkcell.loanservice.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LoanTest {

    @Test
    void shouldMarkLoanAsReturnedSuccessfully() {
        // Arrange
        Loan loan = Loan.create(
                new UserId(UUID.randomUUID()),
                new BookItemId(UUID.randomUUID()),
                new LoanPeriod(LocalDate.now(), LocalDate.now().plusDays(7))
        );

        // Act
        LocalDate returnDate = LocalDate.now().plusDays(3);
        loan.markReturned(returnDate);

        // Assert
        assertEquals(LoanStatus.RETURNED, loan.getStatus(), "Loan status should be RETURNED after returning");
        assertEquals(returnDate, loan.getReturnDate(), "Return date should match the given date");
    }

    @Test
    void shouldThrowExceptionWhenReturnDateIsNull() {
        // Arrange
        Loan loan = Loan.create(
                new UserId(UUID.randomUUID()),
                new BookItemId(UUID.randomUUID()),
                new LoanPeriod(LocalDate.now(), LocalDate.now().plusDays(7))
        );

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> loan.markReturned(null),
                "Should throw exception when returnDate is null");
    }

    @Test
    void shouldThrowExceptionIfLoanIsNotActive() {
        // Arrange
        Loan loan = Loan.create(
                new UserId(UUID.randomUUID()),
                new BookItemId(UUID.randomUUID()),
                new LoanPeriod(LocalDate.now(), LocalDate.now().plusDays(7))
        );
        loan.markReturned(LocalDate.now()); // zaten RETURNED oldu

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> loan.markReturned(LocalDate.now().plusDays(1)),
                "Should not allow returning again if already returned");
    }
}
