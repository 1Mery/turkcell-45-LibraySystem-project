package com.turkcell.user_service.infrastructure.persistence.repository;

import com.turkcell.user_service.infrastructure.persistence.entity.JpaMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMemberRepository extends JpaRepository<JpaMemberEntity, UUID> {
}
