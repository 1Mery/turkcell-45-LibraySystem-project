package com.turkcell.bookservice.application.command.create;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateBookCommand(
        @NotBlank(message = "Title cannot be empty")
        String title,

        @NotBlank(message = "Author cannot be empty")
        String author,

        @NotBlank(message = "ISBN cannot be empty")
        @Size(min = 10, max = 13, message = "ISBN must be 10–13 characters")
        String isbn,

        @NotNull(message = "Total page count is required")
        @Positive(message = "Total page count must be greater than 0")
        Integer totalPage,

        @NotBlank(message = "Publisher cannot be empty")
        String publisher,

        @NotBlank(message = "Image URL cannot be empty")
        String imageUrl,

        @NotBlank(message = "CategoryId cannot be empty")
        String categoryId
) {}
