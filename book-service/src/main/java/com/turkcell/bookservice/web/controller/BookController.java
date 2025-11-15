package com.turkcell.bookservice.web.controller;

import com.turkcell.bookservice.application.command.bokkItemCommands.*;
import com.turkcell.bookservice.application.command.create.CreateBookCommand;
import com.turkcell.bookservice.application.command.create.CreateBookCommandHandler;
import com.turkcell.bookservice.application.command.delete.DeleteBookCommand;
import com.turkcell.bookservice.application.command.delete.DeleteBookCommandHandler;
import com.turkcell.bookservice.application.command.update.UpdateBookCommand;
import com.turkcell.bookservice.application.command.update.UpdateBookCommandHandler;
import com.turkcell.bookservice.application.dto.BookItemDto;
import com.turkcell.bookservice.application.dto.BookListResponseDto;
import com.turkcell.bookservice.application.dto.BookResponseDto;
import com.turkcell.bookservice.application.dto.InventoryDto;
import com.turkcell.bookservice.application.query.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final AddBookCopyHandler addBookCopyHandler;
    private final BorrowBookCommandHandler borrowBookHandler;
    private final ReturnBookHandler returnBookHandler;
    private final GetInventoryHandler getInventoryHandler;

    public BookController(CreateBookCommandHandler createBookHandler,
                          UpdateBookCommandHandler updateBookHandler,
                          DeleteBookCommandHandler deleteBookHandler,
                          GetAllBookQueryHandler getAllHandler,
                          GetBookByIdHandler getByIdHandler,
                          GetBookPagedQueryHandler getPagedHandler,
                          AddBookCopyHandler addBookCopyHandler,
                          BorrowBookCommandHandler borrowBookHandler,
                          ReturnBookHandler returnBookHandler,
                          GetInventoryHandler getInventoryHandler) {
        this.createBookHandler = createBookHandler;
        this.updateBookHandler = updateBookHandler;
        this.deleteBookHandler = deleteBookHandler;
        this.getAllHandler = getAllHandler;
        this.getByIdHandler = getByIdHandler;
        this.getPagedHandler = getPagedHandler;
        this.addBookCopyHandler = addBookCopyHandler;
        this.borrowBookHandler = borrowBookHandler;
        this.returnBookHandler = returnBookHandler;
        this.getInventoryHandler = getInventoryHandler;
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
        deleteBookHandler.delete(new DeleteBookCommand(id));
    }

    // GetBookByID
    @GetMapping("/{id}")
    public BookResponseDto getBookById(@PathVariable UUID id) {
        return getByIdHandler.getBook(new GetBookByIdQuery(id));
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

    // add new copy
    @PostMapping("/{bookId}/copies")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCopy(@PathVariable UUID bookId) {
        addBookCopyHandler.addCopy(new AddBookCopyCommand(bookId));
    }

    // borrow book
    @PostMapping("/{bookId}/borrow")
    public ResponseEntity<Void> borrowBook(@PathVariable UUID bookId) {
        borrowBookHandler.borrowBook(new BorrowBookCommand(bookId));
        return ResponseEntity.ok().build();
    }

    //  return book
    @PostMapping("/{bookId}/return/{itemId}")
    public ResponseEntity<Void> returnBook(@PathVariable UUID bookId, @PathVariable UUID itemId) {
        returnBookHandler.returnBook(new ReturnBookCommand(bookId, itemId));
        return ResponseEntity.ok().build();
    }

    // book stock situations
    @GetMapping("/{bookId}/inventory")
    public ResponseEntity<InventoryDto> getInventory(@PathVariable UUID bookId) {
        InventoryDto dto = getInventoryHandler.handle(new GetInventoryQuery(bookId));
        return ResponseEntity.ok(dto);
    }

    // bookItem details (LoanService çağırır)
    @GetMapping("/items/{itemId}")
    public BookItemResponse getBookItem(@PathVariable UUID itemId) {
        return getBookItemHandler.getById(itemId);
    }

    // sadece title döner (Notification için)
    @GetMapping("/{bookId}/title")
    public String getBookTitle(@PathVariable UUID bookId) {
        return getBookTitleHandler.getTitle(bookId);
    }

    // bookItem  LOANED
    @PatchMapping("/items/{itemId}/loan")
    public void markAsLoaned(@PathVariable UUID itemId) {
        borrowBookHandler.markAsLoaned(itemId);
    }

    // bookItem  AVAILABLE
    @PatchMapping("/items/{itemId}/return")
    public void markAsReturned(@PathVariable UUID itemId) {
        returnBookHandler.markAsReturned(itemId);
    }

}
