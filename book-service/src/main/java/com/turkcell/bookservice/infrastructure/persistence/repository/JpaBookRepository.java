package com.turkcell.bookservice.infrastructure.persistence.repository;

import com.turkcell.bookservice.infrastructure.persistence.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaBookRepository extends JpaRepository<BookEntity, UUID> {
    List<BookEntity> findByCategoryId(UUID categoryId);
}
