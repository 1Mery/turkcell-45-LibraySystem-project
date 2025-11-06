package com.turkcell.bookservice.application.mapper;

import com.turkcell.bookservice.application.dto.BookItemDto;
import com.turkcell.bookservice.domain.model.BookItem;

public class BookItemMapper {
    public static BookItemDto toDto(BookItem item) {
        return new BookItemDto(
                item.getId().value(),
                item.getStatus().name()
        );
    }
}
