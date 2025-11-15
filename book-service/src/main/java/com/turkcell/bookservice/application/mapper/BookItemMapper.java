package com.turkcell.bookservice.application.mapper;

import com.turkcell.bookservice.application.dto.BookItemDto;
import com.turkcell.bookservice.domain.model.Book;
import com.turkcell.bookservice.domain.model.BookItem;
import com.turkcell.bookservice.infrastructure.persistence.entity.BookItemEntity;
import org.springframework.stereotype.Component;

@Component
public class BookItemMapper {

    public BookItemDto toItemDto(BookItem item, Book book) {
        return new BookItemDto(
                item.getId().value(),
                item.getBookId().value(),
                book.getTitle(),
                item.getStatus().name()
        );
    }

    public BookItemEntity toItemEntity(BookItem item) {
        BookItemEntity entity = new BookItemEntity();
        entity.setId(item.getId().value());
        entity.setBookId(item.getBookId().value());
        entity.setStatus(item.getStatus());
        return entity;
    }

}
