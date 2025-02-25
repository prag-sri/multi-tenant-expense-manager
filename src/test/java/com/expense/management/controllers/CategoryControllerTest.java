package com.expense.management.controllers;

import com.expense.management.models.Category;
import com.expense.management.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private Category category1;
    private Category category2;

    @BeforeEach
    void setup(){
        category1 = new Category(1L, "Food", null, null);
        category2 = new Category(2L, "Transport", null, null);
    }

    @Test
    void testGetAllCategories() {
        // Given
        List<Category> categoryList = Arrays.asList(category1, category2);

        // Mockito's when is used to mock behaviour
        // It tells mock categoryService that when getAllCategories() is called, it should return categoryList
        when(categoryService.getAllCategories()).thenReturn(categoryList);

        // When - invoking method under test
        ResponseEntity<List<Category>> response = categoryController.getAllCategories();

        // Then - verify expected outcomes
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Food", response.getBody().get(0).getName());
        assertEquals(200, response.getStatusCode().value());

        // Mockito's verify method ensures categoryService.getAllCategories() was called exactly once.
        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void testCreateCategory() {
        // Given
        // Tells Mockito to do nothing when categoryService.createCategory() is called.
        doNothing().when(categoryService).createCategory(any(Category.class));

        // When
        ResponseEntity<String> response = categoryController.createCategory(category1);

        // Then
        assertEquals(201, response.getStatusCode().value());
        assertEquals("Category created successfully!", response.getBody());

        verify(categoryService,times(1)).createCategory(category1);
    }

    @Test
    void testDeleteCategory(){
        // Given
        doNothing().when(categoryService).deleteCategory(1L);

        // When
        ResponseEntity<String> response = categoryController.deleteCategory(1L);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Category deleted successfully!", response.getBody());

        verify(categoryService, times(1)).deleteCategory(1L);
    }
}
