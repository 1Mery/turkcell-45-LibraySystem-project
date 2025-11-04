package com.turkcell.bookservice.web.controller;

import com.turkcell.bookservice.application.command.create.CategoryCreateCommand;
import com.turkcell.bookservice.application.command.create.CreateCategoryCommandHandler;
import com.turkcell.bookservice.application.command.delete.DeleteCategoryCommand;
import com.turkcell.bookservice.application.command.delete.DeleteCategoryCommandHandler;
import com.turkcell.bookservice.application.dto.CategoryResponseDto;
import com.turkcell.bookservice.application.query.GetAllCategoriesQuery;
import com.turkcell.bookservice.application.query.GetAllCategoriesQueryHandler;
import com.turkcell.bookservice.application.query.GetCategoryByIdQuery;
import com.turkcell.bookservice.application.query.GetCategoryByIdQueryHandler;
import com.turkcell.bookservice.domain.model.Category;
import com.turkcell.bookservice.domain.model.CategoryId;
import com.turkcell.bookservice.domain.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CreateCategoryCommandHandler createHandler;
    private final DeleteCategoryCommandHandler deleteHandler;
    private final GetAllCategoriesQueryHandler getAllHandler;
    private final GetCategoryByIdQueryHandler getByIdHandler;

    public CategoryController(
            CreateCategoryCommandHandler createHandler,
            DeleteCategoryCommandHandler deleteHandler,
            GetAllCategoriesQueryHandler getAllHandler,
            GetCategoryByIdQueryHandler getByIdHandler
    ) {
        this.createHandler = createHandler;
        this.deleteHandler = deleteHandler;
        this.getAllHandler = getAllHandler;
        this.getByIdHandler = getByIdHandler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto createCategory(@RequestBody CategoryCreateCommand command) {
        return createHandler.createCategory(command);
    }

    @GetMapping
    public List<CategoryResponseDto> getAllCategories() {
        return getAllHandler.listCategory();
    }

    @GetMapping("/{id}")
    public CategoryResponseDto getCategoryById(@PathVariable UUID id) {
        GetCategoryByIdQuery query = new GetCategoryByIdQuery(id);
        return getByIdHandler.getIdCategory(query);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable UUID id) {
        DeleteCategoryCommand command = new DeleteCategoryCommand(id);
        deleteHandler.deleteCategory(command);
    }
}