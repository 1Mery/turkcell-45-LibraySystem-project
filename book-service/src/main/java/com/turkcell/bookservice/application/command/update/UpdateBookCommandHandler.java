package com.turkcell.bookservice.application.command.update;

import com.turkcell.bookservice.application.command.InventoryCalculator;
import com.turkcell.bookservice.application.dto.BookItemDto;
import com.turkcell.bookservice.application.dto.BookResponseDto;
import com.turkcell.bookservice.application.dto.InventoryDto;
import com.turkcell.bookservice.application.mapper.BookItemMapper;
import com.turkcell.bookservice.application.mapper.BookResponseMapper;
import com.turkcell.bookservice.domain.model.Book;
import com.turkcell.bookservice.domain.model.BookId;
import com.turkcell.bookservice.domain.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UpdateBookCommandHandler {

    private final BookRepository bookRepository;
    private final BookItemMapper itemMapper;
    private final InventoryCalculator inventoryCalculator;
    private final BookResponseMapper responseMapper;

    public UpdateBookCommandHandler(BookRepository bookRepository,
                                    BookItemMapper itemMapper,
                                    InventoryCalculator inventoryCalculator,
                                    BookResponseMapper responseMapper) {
        this.bookRepository = bookRepository;
        this.itemMapper = itemMapper;
        this.inventoryCalculator = inventoryCalculator;
        this.responseMapper = responseMapper;
    }

    public BookResponseDto update(UpdateBookCommand command) {
        BookId id = new BookId(UUID.fromString(command.id()));
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        if (command.title() != null && !command.title().isBlank())
            book.renameTitle(command.title());

        if (command.publisher() != null && !command.publisher().isBlank())
            book.changePublisher(command.publisher());

        if (command.imageUrl() != null && !command.imageUrl().isBlank())
            book.updateImageUrl(command.imageUrl());

        Book updated = bookRepository.save(book);

        List<BookItemDto> items = updated.getItems().stream()
                .map(itemMapper::toDto)
                .toList();

        InventoryDto inventory = inventoryCalculator.calculate(updated);

        return responseMapper.toDto(updated, items, inventory);
    }
}
