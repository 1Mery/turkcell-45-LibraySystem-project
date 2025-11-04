package com.turkcell.bookservice.application.query;

import com.turkcell.bookservice.application.dto.CategoryResponseDto;
import com.turkcell.bookservice.domain.model.Category;
import com.turkcell.bookservice.domain.model.CategoryId;
import com.turkcell.bookservice.domain.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class GetCategoryByIdQueryHandler {

    private final CategoryRepository categoryRepository;

    public GetCategoryByIdQueryHandler(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDto getIdCategory(GetCategoryByIdQuery query) {
        CategoryId id=new CategoryId(query.id());
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        return new CategoryResponseDto(category.getId().value(), category.getName());
    }
}