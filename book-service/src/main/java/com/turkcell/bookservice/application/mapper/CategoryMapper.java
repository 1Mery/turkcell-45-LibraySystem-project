package com.turkcell.bookservice.application.mapper;

import com.turkcell.bookservice.application.dto.CategoryResponseDto;
import com.turkcell.bookservice.domain.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponseDto toResponse(Category category) {
        return new CategoryResponseDto(category.getId().value(), category.getName());
    }
}
