package com.turkcell.bookservice.infrastructure.persistence.mapper;

import com.turkcell.bookservice.domain.model.Book;
import com.turkcell.bookservice.domain.model.BookId;
import com.turkcell.bookservice.domain.model.CategoryId;
import com.turkcell.bookservice.domain.model.Isbn;
import com.turkcell.bookservice.infrastructure.persistence.entity.BookEntity;
import org.springframework.stereotype.Component;

@Component
public class BookEntityMapper {

    // Domain → Entity
    public BookEntity toEntity(Book book) {
        BookEntity entity=new BookEntity();
        entity.setId(book.getId().value());
        entity.setTitle(book.getTitle());
        entity.setAuthor(book.getAuthor());
        entity.setIsbn(book.getIsbn().isbn());
        entity.setTotalPage(book.getTotalPage());
        entity.setPublisher(book.getPublisher());
        entity.setImageUrl(book.getImageUrl());
        entity.setCategoryId(book.getCategoryId().value());
        return entity;
    }

    // Entity → Domain
    public Book toDomain(BookEntity entity) {
        return Book.rehydrate(
                new BookId(entity.getId()),
                entity.getTitle(),
                entity.getAuthor(),
                new Isbn(entity.getIsbn()),
                entity.getTotalPage(),
                entity.getPublisher(),
                entity.getImageUrl(),
                new CategoryId(entity.getCategoryId())
        );
    }
}
