package com.turkcell.bookservice.infrastructure.persistence.repository;

import com.turkcell.bookservice.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaCategoryRepository extends JpaRepository<CategoryEntity, UUID> {
    boolean existsByName(String name);
    Optional<CategoryEntity> findByName(String name);

}

