package com.turkcell.bookservice.application.mapper;

import com.turkcell.bookservice.application.dto.InventoryDto;
import com.turkcell.bookservice.domain.model.Book;

public class InventoryMapper {
    public static InventoryDto toDto(Book book) {
        int total = book.getItems().size();
        int available = (int) book.getItems().stream().filter(i -> i.getStatus().name().equals("AVAILABLE")).count();
        return new InventoryDto(total, available);
    }
}
