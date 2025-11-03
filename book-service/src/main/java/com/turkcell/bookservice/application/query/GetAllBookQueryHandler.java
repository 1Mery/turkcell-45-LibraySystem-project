package com.turkcell.bookservice.application.query;

import com.turkcell.bookservice.application.dto.BookListResponseDto;
import com.turkcell.bookservice.application.mapper.GetAllBookMapper;
import com.turkcell.bookservice.domain.model.Book;
import com.turkcell.bookservice.domain.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllBookQueryHandler {

    private final BookRepository bookRepository;
    private final GetAllBookMapper mapper;

    public GetAllBookQueryHandler(BookRepository bookRepository, GetAllBookMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    public List<BookListResponseDto> listBook(GetAllBookQuery query) {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(mapper::toListBook)
                .toList();
    }
}
