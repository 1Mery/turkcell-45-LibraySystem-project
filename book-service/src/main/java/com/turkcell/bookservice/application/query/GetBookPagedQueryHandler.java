package com.turkcell.bookservice.application.query;

import com.turkcell.bookservice.application.command.InventoryCalculator;
import com.turkcell.bookservice.application.dto.BookResponseDto;
import com.turkcell.bookservice.application.dto.InventoryDto;
import com.turkcell.bookservice.application.mapper.BookItemMapper;
import com.turkcell.bookservice.application.mapper.BookResponseMapper;
import com.turkcell.bookservice.application.mapper.GetBookPagedMapper;
import com.turkcell.bookservice.domain.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetBookPagedQueryHandler {

    private final BookRepository bookRepository;
    private final BookItemMapper bookItemMapper;
    private final InventoryCalculator inventoryCalculator;
    private final BookResponseMapper responseMapper;

    public GetBookPagedQueryHandler(BookRepository bookRepository,
                                    BookItemMapper bookItemMapper,
                                    InventoryCalculator inventoryCalculator,
                                    BookResponseMapper responseMapper) {
        this.bookRepository = bookRepository;
        this.bookItemMapper = bookItemMapper;
        this.inventoryCalculator = inventoryCalculator;
        this.responseMapper = responseMapper;
    }

    public List<BookResponseDto> pageBook(int page, int size) {

        var books = bookRepository.findAllPaged(page, size); // domain Book listesi

        return books.stream()
                .map(book -> {
                    // 1) BookItem -> BookItemDto
                    var items = book.getItems().stream()
                            .map(bookItemMapper::toDto)
                            .toList();

                    InventoryDto inventory = inventoryCalculator.calculate(book);

                    return responseMapper.toDto(book, items, inventory);
                })
                .toList();
    }
}