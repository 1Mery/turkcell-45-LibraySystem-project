package com.turkcell.bookservice.application.query;

import com.turkcell.bookservice.application.command.InventoryCalculator;
import com.turkcell.bookservice.application.dto.BookItemDto;
import com.turkcell.bookservice.application.dto.BookResponseDto;
import com.turkcell.bookservice.application.dto.InventoryDto;
import com.turkcell.bookservice.application.exception.BookNotFoundException;
import com.turkcell.bookservice.application.mapper.GetBookByIdMapper;
import com.turkcell.bookservice.domain.model.Book;
import com.turkcell.bookservice.domain.model.BookId;
import com.turkcell.bookservice.domain.model.BookItemStatus;
import com.turkcell.bookservice.domain.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class GetBookByIdHandler {
    private final BookRepository bookRepository;
    private final GetBookByIdMapper mapper;
    private final InventoryCalculator inventoryCalculator;

    public GetBookByIdHandler(BookRepository bookRepository, GetBookByIdMapper mapper, InventoryCalculator inventoryCalculator) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
        this.inventoryCalculator = inventoryCalculator;
    }

    public BookResponseDto getBook(GetBookByIdQuery query) {
        Book book = bookRepository.findById(new BookId(query.id()))
                .orElseThrow(() -> new BookNotFoundException(query.id()));


        List<BookItemDto> items = book.getItems().stream()
                .map(i -> new BookItemDto(i.getId().value(), i.getStatus().name()))
                .toList();

        InventoryDto inventory = inventoryCalculator.calculate(book);

        return mapper.toResponse(book, items, inventory);
    }

}

