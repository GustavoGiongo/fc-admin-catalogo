package com.fullcycle.admin.catalogo.infrastructure.Api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullcycle.admin.catalogo.ControllerTest;
import com.fullcycle.admin.catalogo.application.category.create.CreateCategoryCommand;
import com.fullcycle.admin.catalogo.application.category.create.CreateCategoryOutput;
import com.fullcycle.admin.catalogo.application.category.create.CreateCategoryUseCase;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.infrastructure.api.CategoryAPI;
import com.fullcycle.admin.catalogo.infrastructure.category.models.CreateCategoryApiInput;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.print.attribute.standard.Media;
import java.util.Objects;

import static org.hamcrest.Matchers.equalTo;

@ControllerTest(controllers = CategoryAPI.class)
public class CategoryApiTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    CreateCategoryUseCase createCategoryUseCase;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() throws Exception {
        final var expectedName ="Filmes";
        final var expectedDescription ="A Categoria mais assistida";
        final var expectedIsActive =true;

            Mockito.when(createCategoryUseCase.execute(Mockito.any())).thenReturn(Either.right(
                    CreateCategoryOutput.from("123")));

        final var anInput = new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        final var request = MockMvcRequestBuilders.post("/categories").contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(anInput));

        this.mvc.perform(request).andDo(MockMvcResultHandlers.print()).andExpectAll(
                MockMvcResultMatchers.status().isCreated(),
                MockMvcResultMatchers.header().string("Location", "/categories/123"),
                MockMvcResultMatchers.header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE),
                MockMvcResultMatchers.jsonPath("$.id",equalTo("123"))
        );

        Mockito.verify(createCategoryUseCase, Mockito.times(1)).execute(
                Mockito.argThat(cmd -> Objects.equals(expectedName, cmd.name())
                && Objects.equals(expectedDescription, cmd.description())
                && Objects.equals(expectedIsActive, cmd.isActive())));
    }

}
