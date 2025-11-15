package com.turkcell.bookservice.application.query;

import com.turkcell.bookservice.domain.model.Book;
import com.turkcell.bookservice.domain.model.BookId;
import com.turkcell.bookservice.domain.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetBookTitleHandler {

    private final BookRepository repository;

    public GetBookTitleHandler(BookRepository repository) {
        this.repository = repository;
    }

    public String getTitle(UUID bookId) {
        Book book = repository.findById(new BookId(bookId))
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        return book.getTitle();
    }
}
