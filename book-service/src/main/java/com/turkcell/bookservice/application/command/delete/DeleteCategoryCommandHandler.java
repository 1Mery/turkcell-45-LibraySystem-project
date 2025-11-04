package com.turkcell.bookservice.application.command.delete;

import com.turkcell.bookservice.domain.model.CategoryId;
import com.turkcell.bookservice.domain.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteCategoryCommandHandler {

    private final CategoryRepository categoryRepository;

    public DeleteCategoryCommandHandler(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void deleteCategory(DeleteCategoryCommand command) {
        UUID id=command.id();
        categoryRepository.delete(new CategoryId(id));
    }
}
