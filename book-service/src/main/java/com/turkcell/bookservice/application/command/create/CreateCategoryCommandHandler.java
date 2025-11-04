package com.turkcell.bookservice.application.command.create;

import com.turkcell.bookservice.application.dto.CategoryResponseDto;
import com.turkcell.bookservice.domain.model.Category;
import com.turkcell.bookservice.domain.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCategoryCommandHandler {

    private final CategoryRepository categoryRepository;

    public CreateCategoryCommandHandler(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDto createCategory(CategoryCreateCommand command) {
        if (categoryRepository.existsByName(command.name())) {
            throw new IllegalArgumentException("Category already exists");
        }

        Category category = Category.create(command.name());
        Category saved = categoryRepository.save(category);

        return new CategoryResponseDto(saved.getId().value(), saved.getName());
    }
}
