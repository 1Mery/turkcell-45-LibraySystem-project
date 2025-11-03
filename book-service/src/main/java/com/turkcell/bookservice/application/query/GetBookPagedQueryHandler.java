package com.turkcell.bookservice.application.query;

import com.turkcell.bookservice.application.dto.BookResponseDto;
import com.turkcell.bookservice.application.mapper.GetBookPagedMapper;
import com.turkcell.bookservice.domain.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetBookPagedQueryHandler {

    private final BookRepository bookRepository;
    private final GetBookPagedMapper mapper;

    public GetBookPagedQueryHandler(BookRepository bookRepository, GetBookPagedMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    public List<BookResponseDto> pageBook(int page, int size) {
        var books = bookRepository.findAllPaged(page, size);
        return books.stream()
                .map(mapper::toResponse)
                .toList();
    }
}