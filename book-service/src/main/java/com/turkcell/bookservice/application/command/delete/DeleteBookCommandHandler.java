package com.turkcell.bookservice.application.command.delete;

import com.turkcell.bookservice.domain.model.BookId;
import com.turkcell.bookservice.domain.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteBookCommandHandler {

    private final BookRepository bookRepository;

    public DeleteBookCommandHandler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void delete(DeleteBookCommand command) {
        BookId id = new BookId(UUID.fromString(command.id()));
        bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        bookRepository.delete(id);
    }
}
