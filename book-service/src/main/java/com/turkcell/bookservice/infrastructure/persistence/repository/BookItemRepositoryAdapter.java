package com.turkcell.bookservice.infrastructure.persistence.repository;

import com.turkcell.bookservice.domain.model.BookId;
import com.turkcell.bookservice.domain.model.BookItem;
import com.turkcell.bookservice.domain.model.BookItemId;
import com.turkcell.bookservice.domain.repository.BookItemRepository;
import com.turkcell.bookservice.infrastructure.persistence.entity.BookItemEntity;
import com.turkcell.bookservice.infrastructure.persistence.mapper.BookEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BookItemRepositoryAdapter implements BookItemRepository {

    private final JpaBookItemRepository repository;
    private final BookEntityMapper mapper;

    public BookItemRepositoryAdapter(JpaBookItemRepository repository, BookEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public BookItem save(BookItem bookItem) {
        BookItemEntity save=repository.save(mapper.toItemEntity(bookItem));
        return  mapper.toItemDomain(save);
    }

    @Override
    public Optional<BookItem> findById(BookItemId bookItemId) {
        return repository.findById(bookItemId.value()).
                map(bookItem -> BookItem.rehydrate(
                        new BookItemId(bookItem.getId()),
                        new BookId(bookItem.getId()),
                        bookItem.getStatus()
                ));
    }
}
