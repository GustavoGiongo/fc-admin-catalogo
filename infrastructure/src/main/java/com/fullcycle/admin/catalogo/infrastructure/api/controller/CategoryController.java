package com.fullcycle.admin.catalogo.infrastructure.api.controller;

import com.fullcycle.admin.catalogo.application.category.create.CreateCategoryCommand;
import com.fullcycle.admin.catalogo.application.category.create.CreateCategoryOutput;
import com.fullcycle.admin.catalogo.application.category.create.CreateCategoryUseCase;
import com.fullcycle.admin.catalogo.domain.pagination.Pagination;
import com.fullcycle.admin.catalogo.domain.validation.handler.Notification;
import com.fullcycle.admin.catalogo.infrastructure.api.CategoryAPI;
import com.fullcycle.admin.catalogo.infrastructure.category.models.CreateCategoryApiInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.function.Function;

@RestController
public class CategoryController implements CategoryAPI {

    private CreateCategoryUseCase createCategoryUseCase;

    public CategoryController(CreateCategoryUseCase useCase) {
        this.createCategoryUseCase = useCase;
    }

    @Override
    public ResponseEntity<?> createCategory(final CreateCategoryApiInput input) {
        final var aCommand = CreateCategoryCommand.with(
                input.name(),
                input.description(),
                input.active() != null ? input.active() : true

        );

        final Function<Notification, ResponseEntity<?>> onError =
        notification -> ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreateCategoryOutput, ResponseEntity<?>> onSucess =
        output -> ResponseEntity.created(URI.create("/categories/"+output.id())).body(output);

        return this.createCategoryUseCase.execute(aCommand)
                .fold(onError, onSucess);
    }

    @Override
    public Pagination<?> ListCategories(String search, int page, int perPage, String sort, String direction) {
        return null;
    }
}
