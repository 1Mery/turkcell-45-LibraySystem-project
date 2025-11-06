package com.turkcell.bookservice.application.command.bokkItemCommands;

import com.turkcell.bookservice.domain.model.Book;
import com.turkcell.bookservice.domain.model.BookId;
import com.turkcell.bookservice.domain.model.BookItemId;
import com.turkcell.bookservice.domain.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class ReturnBookHandler {

    private final BookRepository bookRepository;

    public ReturnBookHandler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void returnBook(ReturnBookCommand cmd) {
        Book book = bookRepository.findById(new BookId(cmd.bookId()))
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        book.returnCopy(new BookItemId(cmd.itemId()));   // domain kuralı
        bookRepository.save(book);
    }
}
