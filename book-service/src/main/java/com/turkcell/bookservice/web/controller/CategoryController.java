package com.turkcell.bookservice.web.controller;

import com.turkcell.bookservice.application.dto.CategoryResponseDto;
import com.turkcell.bookservice.domain.model.Category;
import com.turkcell.bookservice.domain.model.CategoryId;
import com.turkcell.bookservice.domain.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto createCategory(@RequestBody CategoryResponseDto request) {
        if (categoryRepository.existsByName(request.name())) {
            throw new IllegalArgumentException("Category already exists");
        }
        Category category = Category.create(request.name());
        Category saved = categoryRepository.save(category);
        return new CategoryResponseDto(saved.getId().value(), saved.getName());
    }

    @GetMapping
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(c -> new CategoryResponseDto(c.getId().value(), c.getName()))
                .toList();
    }

    @GetMapping("/{id}")
    public CategoryResponseDto getCategoryById(@PathVariable CategoryId id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        return new CategoryResponseDto(category.getId().value(), category.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable CategoryId id) {
        categoryRepository.delete(id);
    }
}
