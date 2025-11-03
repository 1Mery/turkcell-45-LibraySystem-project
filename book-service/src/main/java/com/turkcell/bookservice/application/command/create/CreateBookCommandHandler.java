package com.turkcell.bookservice.application.command.create;

import com.turkcell.bookservice.application.dto.BookResponseDto;
import com.turkcell.bookservice.application.mapper.CreateBookMapper;
import com.turkcell.bookservice.domain.model.Book;
import com.turkcell.bookservice.domain.model.CategoryId;
import com.turkcell.bookservice.domain.model.Isbn;
import com.turkcell.bookservice.domain.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateBookCommandHandler {

    private final BookRepository repository;
    private final CreateBookMapper mapper;

    public CreateBookCommandHandler(BookRepository repository, CreateBookMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public BookResponseDto create(CreateBookCommand command){
        Isbn isbn=new Isbn(command.isbn());
        CategoryId categoryId=new CategoryId(UUID.randomUUID());

        Book book=Book.create(
                command.title(),
                command.author(),
                isbn,
                command.totalPage(),
                command.publisher(),
                command.imageUrl(),
                categoryId
        );
        repository.save(book);
        return mapper.toResponse(book);
    }
}
