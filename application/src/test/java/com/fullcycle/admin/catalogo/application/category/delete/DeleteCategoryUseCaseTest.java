package com.fullcycle.admin.catalogo.application.category.delete;

import com.fullcycle.admin.catalogo.application.category.delete.DefaultDeleteCategoryUseCase;
import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryUseCaseTest {

    @InjectMocks
    private DefaultDeleteCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }
    @Test
    public void givenAValidId_whenCallsDeleteCategory_shouldBeOK() {
        Category aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var expectedId = aCategory.getId();
        Mockito.doNothing().when(categoryGateway).deleteById(Mockito.eq(expectedId));
        assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, Mockito.times(1)).deleteById(expectedId);
    }

    @Test
    public void givenAnInValidId_whenCallsDeleteCategory_shouldBeOK() {

        final var expectedId = CategoryID.from("123");
        Mockito.doNothing().when(categoryGateway).deleteById(Mockito.eq(expectedId));
        assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));
        Mockito.verify(categoryGateway, Mockito.times(1)).deleteById(expectedId);
    }

    @Test
    public void givenAValidId_whenCallsGatewayThrowsException_shouldReturnException() {
        Category aCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var expectedId = aCategory.getId();

        Mockito.doThrow(new IllegalStateException("Gateway error")).
                when(categoryGateway).deleteById(Mockito.eq(expectedId));

        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));
        Mockito.verify(categoryGateway, Mockito.times(1)).deleteById(expectedId);

    }

}
