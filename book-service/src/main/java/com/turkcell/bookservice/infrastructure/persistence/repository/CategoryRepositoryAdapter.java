package com.turkcell.bookservice.infrastructure.persistence.repository;

import com.turkcell.bookservice.domain.model.Category;
import com.turkcell.bookservice.domain.model.CategoryId;
import com.turkcell.bookservice.domain.repository.CategoryRepository;
import com.turkcell.bookservice.infrastructure.persistence.entity.CategoryEntity;
import com.turkcell.bookservice.infrastructure.persistence.mapper.CategoryEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepositoryAdapter implements CategoryRepository {

    private final JpaCategoryRepository repository;
    private final CategoryEntityMapper mapper;

    public CategoryRepositoryAdapter(JpaCategoryRepository repository, CategoryEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Category save(Category category) {
        CategoryEntity savedEntity = repository.save(mapper.toEntity(category));
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Category> findById(CategoryId id) {
        return repository.findById(id.value())
                .map(mapper::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(CategoryId id) {
        repository.deleteById(id.value());
    }

    @Override
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return repository.findByName(name)
                .map(mapper::toDomain);
    }
}
