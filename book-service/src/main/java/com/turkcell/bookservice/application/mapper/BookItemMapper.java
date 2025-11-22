package com.turkcell.bookservice.application.mapper;

import com.turkcell.bookservice.application.dto.BookItemDto;
import com.turkcell.bookservice.domain.model.Book;
import com.turkcell.bookservice.domain.model.BookItem;
import com.turkcell.bookservice.infrastructure.persistence.entity.BookItemEntity;
import org.springframework.stereotype.Component;

@Component
public class BookItemMapper {

    // Domain  DTO
    public BookItemDto toDto(BookItem item) {
        return new BookItemDto(
                item.getId().value(),
                item.getStatus().name()
        );
    }


}
