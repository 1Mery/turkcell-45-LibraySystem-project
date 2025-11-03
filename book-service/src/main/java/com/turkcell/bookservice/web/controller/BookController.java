package com.turkcell.bookservice.web.controller;

import com.turkcell.bookservice.application.command.create.CreateBookCommand;
import com.turkcell.bookservice.application.command.create.CreateBookCommandHandler;
import com.turkcell.bookservice.application.command.delete.DeleteBookCommand;
import com.turkcell.bookservice.application.command.delete.DeleteBookCommandHandler;
import com.turkcell.bookservice.application.command.update.UpdateBookCommand;
import com.turkcell.bookservice.application.command.update.UpdateBookCommandHandler;
import com.turkcell.bookservice.application.dto.BookListResponseDto;
import com.turkcell.bookservice.application.dto.BookResponseDto;
import com.turkcell.bookservice.application.query.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

        private final CreateBookCommandHandler createBookHandler;
        private final UpdateBookCommandHandler updateBookHandler;
        private final DeleteBookCommandHandler deleteBookHandler;
        private final GetAllBookQueryHandler getAllHandler;
        private final GetBookByIdHandler getByIdHandler;
        private final GetBookPagedQueryHandler getPagedHandler;

        public BookController(CreateBookCommandHandler createBookHandler,
                              UpdateBookCommandHandler updateBookHandler,
                              DeleteBookCommandHandler deleteBookHandler,
                              GetAllBookQueryHandler getAllHandler,
                              GetBookByIdHandler getByIdHandler,
                              GetBookPagedQueryHandler getPagedHandler) {
            this.createBookHandler = createBookHandler;
            this.updateBookHandler = updateBookHandler;
            this.deleteBookHandler = deleteBookHandler;
            this.getAllHandler = getAllHandler;
            this.getByIdHandler = getByIdHandler;
            this.getPagedHandler = getPagedHandler;
        }

        // Create Book
        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public BookResponseDto createBook(@RequestBody CreateBookCommand command) {
            return createBookHandler.create(command);
        }

        // Update Book
        @PutMapping("/{id}")
        public BookResponseDto updateBook(@PathVariable UUID id, @RequestBody UpdateBookCommand command) {
            // Update zaten id'yi command içinden kullanabilir
            return updateBookHandler.update(command);
        }

        // Delete Book
        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deleteBook(@PathVariable UUID id) {
            deleteBookHandler.delete(new DeleteBookCommand(id.toString()));
        }

        // GetBookByID
        @GetMapping("/{id}")
        public BookResponseDto getBookById(@PathVariable UUID id) {
            return getByIdHandler.getBook(new GetBookByIdQuery(id.toString()));
        }

        // GetAllBooks
        @GetMapping("/all")
        public List<BookListResponseDto> getAllBooks() {
            return getAllHandler.listBook(new GetAllBookQuery());
        }

        // GetPagedBooks
        @GetMapping
        public List<BookResponseDto> pageBook(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size
        ) {
            return getPagedHandler.pageBook(page,size);
        }
    }
