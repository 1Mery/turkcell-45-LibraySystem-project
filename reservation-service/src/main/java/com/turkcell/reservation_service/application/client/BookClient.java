package com.turkcell.reservation_service.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "book-service", path = "/api/v1/books")
public interface BookClient {

    @GetMapping("/{bookId}/available-copies")
    Integer getAvailableCopies(@PathVariable UUID bookId);

    /**
     * kitabın status'u AVAILABLE degilse
     * rezervasyon oluşturulamaz.
     */
    @GetMapping("/{bookId}/status")
    String getBookItemStatus(@PathVariable UUID bookId);
}
