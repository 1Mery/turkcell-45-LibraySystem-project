package com.turkcell.bookservice.domain.repository;

import com.turkcell.bookservice.domain.model.BookItem;
import com.turkcell.bookservice.domain.model.BookItemId;

import java.util.Optional;

public interface BookItemRepository {
    BookItem save(BookItem bookItem);
    Optional<BookItem> findById(BookItemId bookItemId);
}
