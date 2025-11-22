package com.turkcell.bookservice.application.command.bookItemCommands;

import com.turkcell.bookservice.domain.model.Book;
import com.turkcell.bookservice.domain.model.BookId;
import com.turkcell.bookservice.domain.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BorrowBookCommandHandler {

    private final BookRepository bookRepository;

    public BorrowBookCommandHandler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public void borrowBook(BorrowBookCommand cmd) {
        Book book = bookRepository.findById(new BookId(cmd.bookId()))
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        book.borrowCopy();                // domain kuralı
        bookRepository.save(book);
    }
}
