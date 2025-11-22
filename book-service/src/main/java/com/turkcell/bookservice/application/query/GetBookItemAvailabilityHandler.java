package com.turkcell.bookservice.application.query;

import com.turkcell.bookservice.domain.model.BookItem;
import com.turkcell.bookservice.domain.model.BookItemId;
import com.turkcell.bookservice.domain.model.BookItemStatus;
import com.turkcell.bookservice.domain.repository.BookItemRepository;
import org.springframework.stereotype.Service;

@Service
public class GetBookItemAvailabilityHandler {

    private final BookItemRepository bookItemRepository;

    public GetBookItemAvailabilityHandler(BookItemRepository bookItemRepository) {
        this.bookItemRepository = bookItemRepository;
    }

    public boolean isAvailable(GetBookItemAvailabilityQuery query) {
        BookItem item = bookItemRepository.findById(new BookItemId(query.itemId()))
                .orElseThrow(() -> new IllegalArgumentException("BookItem not found"));

        return item.getStatus() == BookItemStatus.AVAILABLE;
    }
}
