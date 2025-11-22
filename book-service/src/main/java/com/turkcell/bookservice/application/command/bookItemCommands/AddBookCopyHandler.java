package com.turkcell.bookservice.application.command.bookItemCommands;

import com.turkcell.bookservice.domain.model.Book;
import com.turkcell.bookservice.domain.model.BookId;
import com.turkcell.bookservice.domain.model.BookItem;
import com.turkcell.bookservice.domain.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class AddBookCopyHandler {

    private final BookRepository bookRepository;

    public AddBookCopyHandler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addCopy(AddBookCopyCommand cmd) {
        Book book = bookRepository.findById(new BookId(cmd.bookId()))
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        BookItem newCopy=BookItem.create(book.getId());

        book.addCopy(newCopy);

        bookRepository.save(book);
    }
}
