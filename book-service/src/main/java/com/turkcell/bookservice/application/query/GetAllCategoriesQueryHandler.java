package com.turkcell.bookservice.application.query;

import com.turkcell.bookservice.application.dto.CategoryResponseDto;
import com.turkcell.bookservice.domain.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllCategoriesQueryHandler {

    private final CategoryRepository categoryRepository;

    public GetAllCategoriesQueryHandler(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponseDto> listCategory() {
        return categoryRepository.findAll()
                .stream()
                .map(c -> new CategoryResponseDto(c.getId().value(), c.getName()))
                .toList();
    }
}
