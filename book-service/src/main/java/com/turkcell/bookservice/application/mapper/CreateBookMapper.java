package com.turkcell.bookservice.application.mapper;

import com.turkcell.bookservice.application.dto.BookItemDto;
import com.turkcell.bookservice.application.dto.BookResponseDto;
import com.turkcell.bookservice.application.dto.InventoryDto;
import com.turkcell.bookservice.domain.model.Book;
import com.turkcell.bookservice.domain.model.BookItem;
import com.turkcell.bookservice.domain.model.BookItemStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateBookMapper {

    private final BookResponseMapper responseMapper;

    public CreateBookMapper(BookResponseMapper responseMapper) {
        this.responseMapper = responseMapper;
    }

        public BookResponseDto toDto(Book book,
                                     List<BookItemDto> items,
                                     InventoryDto inventory) {
            return responseMapper.toDto(book, items, inventory);
        }
    }

