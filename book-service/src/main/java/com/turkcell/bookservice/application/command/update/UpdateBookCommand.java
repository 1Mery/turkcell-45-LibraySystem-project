package com.turkcell.bookservice.application.command.update;

import jakarta.validation.constraints.NotBlank;

public record UpdateBookCommand(
        @NotBlank(message = "Book ID cannot be empty")
        String id,
        @NotBlank(message = "Titlecannot be empty")
        String title,
        @NotBlank(message = "Author cannot be empty")
        String author,
        @NotBlank(message = "Publisher cannot be empty")
        String publisher,
        @NotBlank(message = "ImageUrl cannot be empty")
        String imageUrl
) {}