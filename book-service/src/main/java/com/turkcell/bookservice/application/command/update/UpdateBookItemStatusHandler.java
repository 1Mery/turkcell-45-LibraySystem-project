package com.turkcell.bookservice.application.command.update;

import com.turkcell.bookservice.domain.model.BookItem;
import com.turkcell.bookservice.domain.model.BookItemId;
import com.turkcell.bookservice.domain.repository.BookItemRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateBookItemStatusHandler {

    private final BookItemRepository repository;

    public UpdateBookItemStatusHandler(BookItemRepository repository) {
        this.repository = repository;
    }

    public void markAsLoaned(UUID itemId) {

        BookItem item = repository.findById(new BookItemId(itemId))
                .orElseThrow(() -> new IllegalArgumentException("BookItem not found"));

        item.markBorrowed();

        repository.save(item);
    }

    public void markAsReturned(UUID itemId) {

        BookItem item = repository.findById(new BookItemId(itemId))
                .orElseThrow(() -> new IllegalArgumentException("BookItem not found"));

        item.markReturned();

        repository.save(item);
    }
}
