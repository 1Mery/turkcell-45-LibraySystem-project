package com.turkcell.bookservice.application.mapper;

import com.turkcell.bookservice.application.dto.BookListResponseDto;
import com.turkcell.bookservice.domain.model.Book;
import org.springframework.stereotype.Component;

@Component
public class GetAllBookMapper {
    public BookListResponseDto toListBook(Book book) {
        // Liste görünümünde az veri dönmek için
        return new BookListResponseDto(
                book.getId().value(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getImageUrl()
        );
    }
}
