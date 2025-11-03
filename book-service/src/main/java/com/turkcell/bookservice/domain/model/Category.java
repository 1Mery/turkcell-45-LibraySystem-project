package com.turkcell.bookservice.domain.model;

public class Category {
    private final CategoryId id;
    private String name;

    private Category(CategoryId id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Category create(String name) {
        validateName(name);
        return new Category(CategoryId.generate(), name);
    }

    public static Category rehydrate(CategoryId id, String name) {
        return new Category(id, name);
    }


    private static void validateName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Category name cannot be empty");
    }

    public void rename(String newName) {
        validateName(newName);
        this.name = newName;
    }

    public CategoryId getId() {
        return id;
    }

    public String getName() {
        return name;
    }


}
