package com.turkcell.bookservice.domain.model;

public class BookItem {

    private final BookItemId id;
    private final BookId bookId;
    private BookItemStatus status;

    private BookItem(BookItemId id, BookId bookId, BookItemStatus status) {
        this.id = id;
        this.bookId = bookId;
        this.status = status;
    }

    public static BookItem create(BookId bookId) {
        return new BookItem(
                BookItemId.generate(),
                bookId,
                BookItemStatus.AVAILABLE
        );
    }

    public static BookItem rehydrate(BookItemId id, BookId bookId, BookItemStatus status) {
        return new BookItem(id, bookId, status);
    }

    public boolean isAvailable() {
        return status == BookItemStatus.AVAILABLE;
    }

    public void markBorrowed() {
        if (!isAvailable()) {
            throw new IllegalStateException("This book copy is not available to borrow.");
        }
        this.status = BookItemStatus.LOANED;
    }

    public void markReturned() {
        if (status == BookItemStatus.LOANED){
            this.status = BookItemStatus.AVAILABLE;
        }
    }

    public BookItemStatus getStatus() {
        return status;
    }

    public BookItemId getId() {
        return id;
    }

    public BookId getBookId() {
        return bookId;
    }
}
