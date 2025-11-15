package com.turkcell.bookservice.infrastructure.persistence.repository;

import com.turkcell.bookservice.infrastructure.persistence.entity.BookItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaBookItemRepository extends JpaRepository<BookItemEntity, UUID> {
}
