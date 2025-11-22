package com.turkcell.bookservice.application.command;

import com.turkcell.bookservice.application.dto.InventoryDto;
import com.turkcell.bookservice.domain.model.Book;
import com.turkcell.bookservice.domain.model.BookItemStatus;
import org.springframework.stereotype.Component;

@Component
public class InventoryCalculator {

    public InventoryDto calculate(Book book) {

        int total = book.getItems().size();
        int available = (int) book.getItems().stream()
                .filter(item -> item.getStatus() == BookItemStatus.AVAILABLE)
                .count();
        int loaned = (int) book.getItems().stream()
                .filter(item -> item.getStatus() == BookItemStatus.LOANED)
                .count();

        return new InventoryDto(total, available, loaned);
    }
}

