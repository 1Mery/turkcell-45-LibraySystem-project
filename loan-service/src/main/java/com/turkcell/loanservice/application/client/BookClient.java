package com.turkcell.loanservice.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(name = "book-service")
public interface BookClient {

    @GetMapping("/api/v1/books/items/{itemId}/title")
    String getBookTitle(@PathVariable UUID itemId);

    @GetMapping("/api/v1/books/items/{id}/available")
    boolean isBookItemAvailable(@PathVariable UUID id);

    @PostMapping("/api/v1/books/items/{itemId}/loan")
    void markLoaned(@PathVariable UUID itemId);

    @PostMapping("/api/v1/books/items/{id}/return")
    void markReturned(@PathVariable UUID id);
}