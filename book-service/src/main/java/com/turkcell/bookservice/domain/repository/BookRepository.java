package com.turkcell.bookservice.domain.repository;

import com.turkcell.bookservice.domain.model.Book;
import com.turkcell.bookservice.domain.model.BookId;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Book save(Book book);
    Optional<Book> findById(BookId bookId);
    List<Book> findAll();
    List<Book> findAllPaged(Integer pageIndex, Integer pageSize);
    void delete(BookId bookId);
}
