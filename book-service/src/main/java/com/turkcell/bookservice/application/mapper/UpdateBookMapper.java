package com.turkcell.bookservice.application.mapper;

import com.turkcell.bookservice.application.dto.BookResponseDto;
import com.turkcell.bookservice.domain.model.Book;
import org.springframework.stereotype.Component;

@Component
public class UpdateBookMapper {

    public BookResponseDto toResponse(Book book) {
        return new BookResponseDto(
                book.getId().value().toString(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn().isbn(),
                book.getTotalPage(),
                book.getPublisher(),
                book.getImageUrl(),
                book.getCategoryId().value().toString()
        );
    }
}
