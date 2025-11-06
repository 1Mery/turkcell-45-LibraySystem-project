package com.turkcell.bookservice.domain.model;

public class BookItem {
    private final BookItemId id;
    private BookItemStatus status;

    private BookItem(BookItemId id, BookItemStatus status) {
        this.id = id;
        this.status = status;
    }

    public static BookItem create() {
        return new BookItem(BookItemId.generate(), BookItemStatus.AVAILABLE);
    }

    public static BookItem rehydrate(BookItemId bookItemsId,BookItemStatus bookStatus){
        return  new BookItem(bookItemsId,bookStatus);
    }

    public boolean isAvailable() {
        return status == BookItemStatus.AVAILABLE;
    }

    public void markBorrowed() {
        if (!isAvailable()) {
            throw new IllegalStateException("This book copy is not available to borrow.");
        }
        this.status = BookItemStatus.BORROWED;
    }

    public void markReturned() {
        if (status == BookItemStatus.BORROWED)
            this.status = BookItemStatus.AVAILABLE;
    }

    public BookItemStatus getStatus() {
        return status;
    }

    public BookItemId getId() {
        return id;
    }
}
