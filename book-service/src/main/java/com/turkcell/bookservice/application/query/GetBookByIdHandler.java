package com.turkcell.bookservice.application.query;

import com.turkcell.bookservice.application.dto.BookResponseDto;
import com.turkcell.bookservice.application.mapper.GetBookByIdMapper;
import com.turkcell.bookservice.domain.model.Book;
import com.turkcell.bookservice.domain.model.BookId;
import com.turkcell.bookservice.domain.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetBookByIdHandler {
    private final BookRepository bookRepository;
    private final GetBookByIdMapper mapper;

    public GetBookByIdHandler(BookRepository bookRepository, GetBookByIdMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    public BookResponseDto getBook(GetBookByIdQuery query) {
        BookId id = new BookId(UUID.fromString(query.id()));
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return mapper.toResponse(book);
    }
}

