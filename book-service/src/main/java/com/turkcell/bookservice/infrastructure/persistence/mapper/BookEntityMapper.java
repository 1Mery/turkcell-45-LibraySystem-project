package com.turkcell.bookservice.infrastructure.persistence.mapper;

import com.turkcell.bookservice.domain.model.*;
import com.turkcell.bookservice.infrastructure.persistence.entity.BookEntity;
import com.turkcell.bookservice.infrastructure.persistence.entity.BookItemEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookEntityMapper {

    // Domain - Entity
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

        List<BookItemEntity> itemEntities = book.getItems().stream()
                .map(this::toItemEntity)
                .collect(Collectors.toList());
        entity.setItems(itemEntities);
        return entity;
    }

    public BookItemEntity toItemEntity(BookItem item) {
        BookItemEntity entity = new BookItemEntity();
        entity.setId(item.getId().value());
        entity.setBookId(item.getBookId().value());
        entity.setStatus(item.getStatus());
        return entity;
    }

    public BookItem toItemDomain(BookItemEntity entity) {
        return BookItem.rehydrate(
                new BookItemId(entity.getId()),
                new BookId(entity.getBookId()),
                entity.getStatus()
        );
    }



    // Entity - Domain
    public Book toDomain(BookEntity entity) {
        Book book = Book.rehydrate(
                new BookId(entity.getId()),
                entity.getTitle(),
                entity.getAuthor(),
                new Isbn(entity.getIsbn()),
                entity.getTotalPage(),
                entity.getPublisher(),
                entity.getImageUrl(),
                new CategoryId(entity.getCategoryId())
        );

        if (entity.getItems() != null) {
            entity.getItems().forEach(item ->
                    book.addCopy(BookItem.rehydrate(
                            new BookItemId(item.getId()),
                            new BookId(item.getBookId()),
                            item.getStatus()
                    ))
            );
        }
        return book;
    }

}
