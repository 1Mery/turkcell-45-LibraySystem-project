package com.turkcell.bookservice.domain.repository;

import com.turkcell.bookservice.domain.model.Category;
import com.turkcell.bookservice.domain.model.CategoryId;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Category save(Category category);
    Optional<Category> findById(CategoryId id);
    List<Category> findAll();
    void delete(CategoryId id);
    boolean existsByName(String name);
    Optional<Category> findByName(String name);
}
