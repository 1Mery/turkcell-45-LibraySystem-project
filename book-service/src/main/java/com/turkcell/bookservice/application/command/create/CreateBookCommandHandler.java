package com.turkcell.bookservice.application.command.create;

import com.turkcell.bookservice.application.command.InventoryCalculator;
import com.turkcell.bookservice.application.dto.BookItemDto;
import com.turkcell.bookservice.application.dto.BookResponseDto;
import com.turkcell.bookservice.application.dto.InventoryDto;
import com.turkcell.bookservice.application.mapper.BookItemMapper;
import com.turkcell.bookservice.application.mapper.BookResponseMapper;
import com.turkcell.bookservice.application.mapper.CreateBookMapper;
import com.turkcell.bookservice.domain.model.Book;
import com.turkcell.bookservice.domain.model.BookItem;
import com.turkcell.bookservice.domain.model.CategoryId;
import com.turkcell.bookservice.domain.model.Isbn;
import com.turkcell.bookservice.domain.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CreateBookCommandHandler {

    private final BookRepository repository;
    private final CreateBookMapper mapper;
    private final InventoryCalculator inventoryCalculator;
    private final BookItemMapper itemMapper;

    public CreateBookCommandHandler(BookRepository repository, CreateBookMapper mapper, InventoryCalculator inventoryCalculator, BookItemMapper itemMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.inventoryCalculator = inventoryCalculator;
        this.itemMapper = itemMapper;
    }

    public BookResponseDto create(CreateBookCommand command){

        Isbn isbn = new Isbn(command.isbn());
        CategoryId categoryId = new CategoryId(UUID.randomUUID());

        Book book = Book.create(
                command.title(),
                command.author(),
                isbn,
                command.totalPage(),
                command.publisher(),
                command.imageUrl(),
                categoryId
        );

        // İlk kopyayı oluştur
        BookItem firstCopy = BookItem.create(book.getId());
        book.addCopy(firstCopy);

        Book saved = repository.save(book);

        // BookItem -> BookItemDto
        List<BookItemDto> itemDtos = saved.getItems().stream()
                .map(itemMapper::toDto)
                .toList();

        // Envanter hesapla
        InventoryDto inventory = inventoryCalculator.calculate(saved);

        // Hepsini tek DTO’ya çevir
        return mapper.toDto(saved, itemDtos, inventory);
    }
}
