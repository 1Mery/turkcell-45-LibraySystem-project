package com.turkcell.bookservice.application.mapper;

import com.turkcell.bookservice.application.dto.BookItemDto;
import com.turkcell.bookservice.application.dto.BookResponseDto;
import com.turkcell.bookservice.application.dto.InventoryDto;
import com.turkcell.bookservice.domain.model.Book;
import com.turkcell.bookservice.domain.model.Category;
import com.turkcell.bookservice.domain.repository.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookResponseMapper {

    private final CategoryRepository categoryRepository;

    public BookResponseMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public BookResponseDto toDto(Book book,
                                 List<BookItemDto> items,
                                 InventoryDto inventory) {

        String categoryName = categoryRepository.findById(book.getCategoryId())
                .map(Category::getName)
                .orElse(null); // kategori yoksa null döner

        return new BookResponseDto(
                book.getId().value(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn().isbn(),
                book.getTotalPage(),
                book.getPublisher(),
                book.getImageUrl(),
                categoryName,
                items,
                inventory
        );
    }
}

