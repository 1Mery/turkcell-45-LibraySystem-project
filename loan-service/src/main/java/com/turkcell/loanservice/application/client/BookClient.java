package com.turkcell.loanservice.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(name = "book-service")
public interface BookClient {

    @GetMapping("/api/v1/books/{id}/title")
    String getBookTitle(@PathVariable UUID id);

    @GetMapping("/api/v1/book-items/{id}/available")
    Boolean isBookItemAvailable(@PathVariable UUID id);

    @PostMapping("/api/v1/book-items/{id}/loan")
    void markBorrowed(@PathVariable UUID id);

    @PostMapping("/api/v1/book-items/{id}/return")
    void markReturned(@PathVariable UUID id);
}