package com.turkcell.bookservice.infrastructure.persistence.entity;

import com.turkcell.bookservice.domain.model.BookItemStatus;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "book_items")
public class BookItemEntity {

    @Id
    private UUID id;

    @Column(name = "book_id", nullable = false)
    private UUID bookId;


    @Enumerated(EnumType.STRING)
    private BookItemStatus status;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BookItemStatus getStatus() {
        return status;
    }

    public void setStatus(BookItemStatus status) {
        this.status = status;
    }

    public UUID getBookId() {
        return bookId;
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }
}
