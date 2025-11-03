package com.turkcell.bookservice.infrastructure.persistence.mapper;

import com.turkcell.bookservice.domain.model.Category;
import com.turkcell.bookservice.domain.model.CategoryId;
import com.turkcell.bookservice.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryEntityMapper {

    // Domain → Entity
    public CategoryEntity toEntity(Category category) {
        CategoryEntity entity = new CategoryEntity();
        entity.setCategoryId(category.getId().value());
        entity.setName(category.getName());
        return entity;
    }

    //Entity → Domain
    public Category toDomain(CategoryEntity entity) {
        return Category.rehydrate(
                new CategoryId(entity.getCategoryId()),
                entity.getName()
        );
    }
}
