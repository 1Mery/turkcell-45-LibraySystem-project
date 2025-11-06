package com.turkcell.bookservice.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Book {
    private final BookId id;
    private String title;
    private String author;
    private Isbn isbn;
    private int totalPage;
    private String publisher;
    private String imageUrl;
    private CategoryId categoryId;
    private final List<BookItem> items = new ArrayList<>();


    public Book(BookId id, String title, String author, Isbn isbn, int totalPage, String publisher, String imageUrl,CategoryId categoryId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.totalPage = totalPage;
        this.publisher = publisher;
        this.imageUrl = imageUrl;
        this.categoryId=categoryId;
    }

public static Book create(String title,String author,Isbn isbn, Integer totalPage, String publisher,String imageUrl,CategoryId categoryId){
    validateTitle(title);
    validateAuthor(author);
    Objects.requireNonNull(isbn,"Isbn cannot be null");
    validateTotalPage(totalPage);
    validatePublisher(publisher);
    validateImageUrl(imageUrl);
    Objects.requireNonNull(categoryId,"CategoryId cannot be null");

    return new Book(BookId.generate(),title,author,isbn,totalPage,publisher,imageUrl,categoryId);
}

public static Book rehydrate(BookId id,String title,String author,Isbn isbn,Integer totalPage, String publisher, String imageUrl,CategoryId categoryId){
        return new Book(id, title, author, isbn, totalPage, publisher, imageUrl,categoryId);
}

private static void validateTitle(String title) {
    if (title == null || title.isBlank())
        throw new IllegalArgumentException("Title cannot be empty");
}

private static void validateAuthor(String author) {
    if (author == null || author.isBlank())
        throw new IllegalArgumentException("Author cannot be empty");
}

private static void validateTotalPage(Integer totalPage) {
    if (totalPage == null || totalPage <= 0)
        throw new IllegalArgumentException("Total pages must be positive");
}

private static void validatePublisher(String publisher) {
    if (publisher == null || publisher.isBlank())
        throw new IllegalArgumentException("Publisher cannot be empty");
}

private static void validateImageUrl(String imageUrl) {
    if (imageUrl == null || !imageUrl.startsWith("http"))
        throw new IllegalArgumentException("Image URL must be valid");
}

//business logics
    public void renameTitle(String newTitle) {
        validateTitle(newTitle);
        this.title = newTitle;
    }

    public void changePublisher(String newPublisher) {
        validatePublisher(newPublisher);
        this.publisher = newPublisher;
    }

    public void updateImageUrl(String newImageUrl) {
        validateImageUrl(newImageUrl);
        this.imageUrl = newImageUrl;
    }

    public void addCopy(BookItem item) {
        Objects.requireNonNull(item, "BookItem cannot be null");
        this.items.add(item);
    }

    public long availableCopiesCount() {
        return items.stream().filter(BookItem::isAvailable).count();
    }

    //Ödünç verilecek kopya var mı
    public boolean hasAvailableCopies() {
        return availableCopiesCount() > 0;
    }

    //kopya ödünç ver
    public void borrowCopy() {
        BookItem available = items.stream()
                .filter(BookItem::isAvailable)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No available copies to borrow"));
        available.markBorrowed();
    }

    public void returnCopy(BookItemId itemId) {
        items.stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .ifPresent(BookItem::markReturned);
    }

    public boolean canBeReserved() {
        return availableCopiesCount() == 0;
    }


    public BookId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Isbn getIsbn() {
        return isbn;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public CategoryId getCategoryId() {
        return categoryId;
    }

    public List<BookItem> getItems() {
        return items;
    }
}

