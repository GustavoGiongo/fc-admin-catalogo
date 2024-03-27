package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.handler.TrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class CategoryTest {
    @Test
    public void givenAnValidParams_whenCallNewCategory_thenInstantianteACategory() {
       final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActive = true;

      final var actualCategory =  Category.newCategory(expectedName, expectedDescription, expectedActive);
      Assertions.assertNotNull(actualCategory);
      Assertions.assertNotNull(actualCategory.getId());
      Assertions.assertEquals(expectedName, actualCategory.getName());
      Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
      Assertions.assertEquals(expectedActive, actualCategory.isActive());
      Assertions.assertNotNull(actualCategory.getCreatedAt());
      Assertions.assertNotNull(actualCategory.getUpdatedAt());
      Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAnInValidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = null;
        final var expectedErrorMessage ="'name' should not be null";
        final var expectedErrorCount = 1;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActive = true;


        final var actualCategory =  Category.newCategory(expectedName, expectedDescription, expectedActive);

       final var actualException =  Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new TrowsValidationHandler()));


        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

    }
    @Test
    public void givenAnInValidEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = "   ";
        final var expectedErrorMessage ="'name' should not be empty";
        final var expectedErrorCount = 1;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActive = true;


        final var actualCategory =  Category.newCategory(expectedName, expectedDescription, expectedActive);

        final var actualException =  Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new TrowsValidationHandler()));


        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

    }

    @Test
    public void givenAnInValidNameLengthLessThan3_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = "fi ";
        final var expectedErrorMessage ="'name' should not be between 3 and 255 characters";
        final var expectedErrorCount = 1;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActive = true;


        final var actualCategory =  Category.newCategory(expectedName, expectedDescription, expectedActive);

        final var actualException =  Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new TrowsValidationHandler()));


        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

    }

    @Test
    public void givenAnInValidNameLengthMoreThan255_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = """
                 Caros amigos, o acompanhamento das preferências de consumo é uma das consequências da" +
                "s regras de conduta normativas. Gostaria de enfatizar que a complexidade dos estudos efetuados cumpre um papel" +
                " essencial na formulação do sistema de participação geral. Assim mesmo, a estrutura atual da organização causa i" +
                "mpacto indireto na reavaliação das diretrizes de desenvolvimento para o futuro.\n""";

        final var expectedErrorMessage ="'name' should not be between 3 and 255 characters";
        final var expectedErrorCount = 1;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActive = true;


        final var actualCategory =  Category.newCategory(expectedName, expectedDescription, expectedActive);

        final var actualException =  Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new TrowsValidationHandler()));


        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

    }

    @Test
    public void givenAnValidEmptyDescription_whenCallNewCategory_thenShouldReceiveError() {
        final var expectedName = "Filmes";
        final var expectedDescription = "";
        final var expectedActive = true;

        final var actualCategory =  Category.newCategory(expectedName, expectedDescription, expectedActive);

        Assertions.assertDoesNotThrow(() ->actualCategory.validate(new TrowsValidationHandler()));
        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAnValidFalseIsActive_whenCallNewCategory_thenShouldReceiveError() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActive = false;

        final var actualCategory =  Category.newCategory(expectedName, expectedDescription, expectedActive);

        Assertions.assertDoesNotThrow(() ->actualCategory.validate(new TrowsValidationHandler()));
        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }


    @Test
    public void giverAnValidActiveCategory_WhenCallDeactivate_thenReturnCategoryInactivated(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActive = false;

        final var aCategory =  Category.newCategory(expectedName, expectedDescription, true);
        Assertions.assertDoesNotThrow(() ->aCategory.validate(new TrowsValidationHandler()));

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        final var actualCategory = aCategory.deactivate();




        Assertions.assertDoesNotThrow(() ->aCategory.validate(new TrowsValidationHandler()));


        Assertions.assertEquals(aCategory.getId(),actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
//        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void giverAnValidInactiveCategory_WhenCallActivate_thenReturnCategoryActivated(){
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActive = true;

        final var aCategory =  Category.newCategory(expectedName, expectedDescription, false);
        Assertions.assertDoesNotThrow(() ->aCategory.validate(new TrowsValidationHandler()));

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        Assertions.assertFalse(aCategory.isActive());
        Assertions.assertNotNull(aCategory.getDeletedAt());

        final var actualCategory = aCategory.activate();

        Assertions.assertDoesNotThrow(() ->aCategory.validate(new TrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(),actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
    //    Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdate_thenReturnCategoryUpdated() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActive = true;

        final var aCategory =  Category.newCategory("Film", "A categoria", false);
        Assertions.assertDoesNotThrow(() ->aCategory.validate(new TrowsValidationHandler()));



        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        final var actualCategory = aCategory.update(expectedName, expectedDescription, expectedActive);

        Assertions.assertDoesNotThrow(() ->aCategory.validate(new TrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(),actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedActive, actualCategory.isActive());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(actualCategory.getDeletedAt());

    }

    @Test
    public void givenAValidCategory_whenUpdateToInactive_thenReturnCategoryUpdated() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActive = false;

        final var aCategory =  Category.newCategory("Film", "A categoria", true);
        Assertions.assertDoesNotThrow(() ->aCategory.validate(new TrowsValidationHandler()));

        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        final var actualCategory = aCategory.update(expectedName, expectedDescription, expectedActive);

        Assertions.assertDoesNotThrow(() ->aCategory.validate(new TrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(),actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedActive, actualCategory.isActive());
        Assertions.assertFalse(aCategory.isActive());
        Assertions.assertNotNull(aCategory.getDeletedAt());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
     //   Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));

    }

    @Test
    public void givenAValidCategory_whenUpdateWithInvalidParams_thenReturnCategoryUpdated() {
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedActive = true;

        final var aCategory =  Category.newCategory("Filmes", "A categoria", expectedActive);

        Assertions.assertDoesNotThrow(() ->aCategory.validate(new TrowsValidationHandler()));

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        final var actualCategory = aCategory.update(expectedName, expectedDescription, expectedActive);

        Assertions.assertEquals(aCategory.getId(),actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedActive, actualCategory.isActive());

        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.getCreatedAt());
   //     Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());
    }


}
