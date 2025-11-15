package com.turkcell.bookservice.infrastructure.persistence.repository;

import com.turkcell.bookservice.domain.model.Book;
import com.turkcell.bookservice.domain.model.BookId;
import com.turkcell.bookservice.domain.model.CategoryId;
import com.turkcell.bookservice.domain.repository.BookRepository;
import com.turkcell.bookservice.infrastructure.persistence.entity.BookEntity;
import com.turkcell.bookservice.infrastructure.persistence.mapper.BookEntityMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryAdapter implements BookRepository {

    private final JpaBookRepository jpaRepository;
    private final BookEntityMapper mapper;

    public BookRepositoryAdapter(JpaBookRepository jpaRepository, BookEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Book save(Book book) {
        BookEntity saved = jpaRepository.save(mapper.toEntity(book));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Book> findById(BookId id) {
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public List<Book> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(BookId id) {
        jpaRepository.deleteById(id.value());
    }

    @Override
    public List<Book> findByCategoryId(CategoryId categoryId) {
        return jpaRepository.findByCategoryId(categoryId.value()).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Book> findAllPaged(Integer pageIndex, Integer pageSize) {
        PageRequest pageable = PageRequest.of(pageIndex, pageSize);
        return jpaRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

}
