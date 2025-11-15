package com.turkcell.bookservice.application.query;

import com.turkcell.bookservice.application.dto.BookItemDto;
import com.turkcell.bookservice.application.mapper.BookItemMapper;
import com.turkcell.bookservice.domain.model.Book;
import com.turkcell.bookservice.domain.model.BookId;
import com.turkcell.bookservice.domain.model.BookItem;
import com.turkcell.bookservice.domain.model.BookItemId;
import com.turkcell.bookservice.domain.repository.BookItemRepository;
import com.turkcell.bookservice.domain.repository.BookRepository;
import org.springframework.stereotype.Service;


@Service
public class GetBookItemHandler {

    private final BookItemRepository bookItemRepository;
    private final BookRepository bookRepository;
    private final BookItemMapper mapper;

    public GetBookItemHandler(BookItemRepository bookItemRepository,
                              BookRepository bookRepository,
                              BookItemMapper mapper) {
        this.bookItemRepository = bookItemRepository;
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    public BookItemDto getById(BookItemId itemId) {

        BookItem item = bookItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("BookItem not found"));

        Book book = bookRepository.findById(new BookId(item.getBookId().value()))
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        return mapper.toItemDto(item, book);
    }
}