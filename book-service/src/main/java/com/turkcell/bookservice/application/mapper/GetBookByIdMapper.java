package com.turkcell.bookservice.application.mapper;

import com.turkcell.bookservice.application.dto.BookItemDto;
import com.turkcell.bookservice.application.dto.BookResponseDto;
import com.turkcell.bookservice.application.dto.InventoryDto;
import com.turkcell.bookservice.domain.model.Book;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetBookByIdMapper {

    private final BookResponseMapper responseMapper;

    public GetBookByIdMapper(BookResponseMapper responseMapper) {
        this.responseMapper = responseMapper;
    }

    public BookResponseDto toResponse(Book book,
                                      List<BookItemDto> items,
                                      InventoryDto inventory) {

        return responseMapper.toDto(book, items, inventory);
    }
}
