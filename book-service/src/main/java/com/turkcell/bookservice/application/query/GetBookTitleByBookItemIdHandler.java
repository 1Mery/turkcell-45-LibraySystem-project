package com.turkcell.bookservice.application.query;

import com.turkcell.bookservice.domain.model.Book;
import com.turkcell.bookservice.domain.model.BookId;
import com.turkcell.bookservice.domain.model.BookItem;
import com.turkcell.bookservice.domain.model.BookItemId;
import com.turkcell.bookservice.domain.repository.BookItemRepository;
import com.turkcell.bookservice.domain.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class GetBookTitleByBookItemIdHandler {

    private final BookItemRepository bookItemRepository;
    private final BookRepository bookRepository;

    public GetBookTitleByBookItemIdHandler(BookItemRepository bookItemRepository,
                                           BookRepository bookRepository) {
        this.bookItemRepository = bookItemRepository;
        this.bookRepository = bookRepository;
    }

    public String getTitle(GetBookTitleByBookItemIdQuery query) {

        // BookItem bul
        BookItem item = bookItemRepository.findById(new BookItemId(query.bookItemId()))
                .orElseThrow(() -> new IllegalArgumentException("BookItem not found"));

        //BookId al
        BookId bookId = item.getBookId();

        //Book bul
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        //Title dön
        return book.getTitle();
    }
}

