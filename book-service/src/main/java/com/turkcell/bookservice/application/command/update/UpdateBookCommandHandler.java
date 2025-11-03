package com.turkcell.bookservice.application.command.update;

import com.turkcell.bookservice.application.dto.BookResponseDto;
import com.turkcell.bookservice.application.mapper.UpdateBookMapper;
import com.turkcell.bookservice.domain.model.Book;
import com.turkcell.bookservice.domain.model.BookId;
import com.turkcell.bookservice.domain.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UpdateBookCommandHandler {
    private final BookRepository bookRepository;
    private final UpdateBookMapper mapper;

    public UpdateBookCommandHandler(BookRepository bookRepository, UpdateBookMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    public BookResponseDto update(UpdateBookCommand command) {
        BookId id = new BookId(UUID.fromString(command.id())); //dışardan string bir id geliyor
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Domain davranışlarını kullanarak değişiklikler
        if (command.title() != null && !command.title().isBlank())
            book.renameTitle(command.title());

        if (command.publisher() != null && !command.publisher().isBlank())
            book.changePublisher(command.publisher());

        if (command.imageUrl() != null && !command.imageUrl().isBlank())
            book.updateImageUrl(command.imageUrl());

        if (command.author() != null && !command.author().isBlank())
            book.renameTitle(command.title());

        Book updated = bookRepository.save(book);
        return mapper.toResponse(updated);
    }
}

