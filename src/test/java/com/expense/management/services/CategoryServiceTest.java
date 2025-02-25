package com.expense.management.services;

import com.expense.management.exceptions.CategoryAlreadyExistsException;
import com.expense.management.models.Category;
import com.expense.management.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category1;
    private Category category2;

    @BeforeEach
    void setup(){
        category1 = new Category(1L, "Food", null, null);
        category2 = new Category(2L, "Transport", null, null);
    }

    @Test
    void testGetAllCategories(){
        // Given
        List<Category> categories = Arrays.asList(category1, category2);
        when(categoryRepository.findAll()).thenReturn(categories);

        // When
        List<Category> result = categoryService.getAllCategories();

        // Then
        assertEquals(2, result.size());
        assertEquals("Food", result.get(0).getName());

        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testCreateCategory_Success(){
        // Given
        when(categoryRepository.existsByName(category1.getName())).thenReturn(false);
        when(categoryRepository.save(category1)).thenReturn(category1);

        // When
        categoryService.createCategory(category1);

        // Then
        verify(categoryRepository, times(1)).save(category1);
    }

    @Test
    void testCreateCategory_Failure_CategoryAlreadyExists(){
        // Given
        when(categoryRepository.existsByName(category1.getName())).thenReturn(true);

        // When & Then
        assertThrows(CategoryAlreadyExistsException.class, () -> categoryService.createCategory(category1));

        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void testDeleteCategory() {
        // Given
        doNothing().when(categoryRepository).deleteById(1L);

        // When
        categoryService.deleteCategory(1L);

        // Then
        verify(categoryRepository, times(1)).deleteById(1L);
    }
}
