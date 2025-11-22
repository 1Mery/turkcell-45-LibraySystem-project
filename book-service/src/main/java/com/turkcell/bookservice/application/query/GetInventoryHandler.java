package com.turkcell.bookservice.application.query;

import com.turkcell.bookservice.application.command.InventoryCalculator;
import com.turkcell.bookservice.application.dto.InventoryDto;
import com.turkcell.bookservice.domain.model.Book;
import com.turkcell.bookservice.domain.model.BookId;
import com.turkcell.bookservice.domain.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class GetInventoryHandler {

    private final BookRepository bookRepository;
    private final InventoryCalculator inventoryCalculator;

    public GetInventoryHandler(BookRepository bookRepository,
                               InventoryCalculator inventoryCalculator) {
        this.bookRepository = bookRepository;
        this.inventoryCalculator = inventoryCalculator;
    }

    public InventoryDto handle(GetInventoryQuery query) {

        Book book = bookRepository.findById(new BookId(query.bookId()))
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        return inventoryCalculator.calculate(book);
    }
}
