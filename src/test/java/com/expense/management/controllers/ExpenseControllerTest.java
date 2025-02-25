package com.expense.management.controllers;

import com.expense.management.models.Expense;
import com.expense.management.services.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExpenseControllerTest {

    @Mock
    private ExpenseService expenseService;

    @InjectMocks
    private ExpenseController expenseController;

    private Expense expense1;
    private Expense expense2;

    @BeforeEach
    void setup(){
        expense1 = new Expense(
                1L,
                "Test Title 1",
                "Test Description",
                new BigDecimal("100.50"),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                false
        );

        expense2 = new Expense(
                2L,
                "Test Title 2",
                "Test Description",
                new BigDecimal("100.50"),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                false
        );
    }

    @Test
    void testCreateExpense(){
        // Given
        when(expenseService.createExpense(expense1)).thenReturn(expense1);

        // When
        ResponseEntity<String> response = expenseController.createExpense(expense1);

        // Then
        assertEquals(201, response.getStatusCode().value());
        assertEquals("Expense created successfully!", response.getBody());

        verify(expenseService, times(1)).createExpense(expense1);
    }

    @Test
    void testGetAllExpenses(){
        // Given
        List<Expense> expenseList = Arrays.asList(expense1, expense2);
        when(expenseService.getAllExpenses()).thenReturn(expenseList);

        // When
        ResponseEntity<List<Expense>> response = expenseController.getAllExpenses();

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals("Test Title 1", response.getBody().get(0).getTitle());

        verify(expenseService, times(1)).getAllExpenses();
    }

    @Test
    void testGetExpenseById(){
        // Given
        when(expenseService.getExpenseById(1L)).thenReturn(expense1);

        // When
        ResponseEntity<Expense> response = expenseController.getExpenseById(1L);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Test Title 1", response.getBody().getTitle());

        verify(expenseService, times(1)).getExpenseById(1L);
    }

    @Test
    void testGetExpensesByUser(){
        // Given
        List<Expense> expenseList = Arrays.asList(expense1, expense2);
        when(expenseService.getExpensesByUser(1L)).thenReturn(expenseList);

        // When
        ResponseEntity<List<Expense>> response = expenseController.getExpensesByUser(1L);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals("Test Title 1", response.getBody().get(0).getTitle());

        verify(expenseService, times(1)).getExpensesByUser(1L);
    }

    @Test
    void testGetExpensesByCompany(){
        // Given
        List<Expense> expenseList = Arrays.asList(expense1, expense2);
        when(expenseService.getExpensesByCompany(1L)).thenReturn(expenseList);

        // When
        ResponseEntity<List<Expense>> response = expenseController.getExpensesByCompany(1L);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals("Test Title 1", response.getBody().get(0).getTitle());

        verify(expenseService, times(1)).getExpensesByCompany(1L);
    }

    @Test
    void testGetExpensesByUserAndCompany(){
        // Given
        List<Expense> expenseList = Arrays.asList(expense1, expense2);
        when(expenseService.getExpensesByUserAndCompany(1L, 1L)).thenReturn(expenseList);

        // When
        ResponseEntity<List<Expense>> response = expenseController.getExpensesByUserAndCompany(1L, 1L);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals("Test Title 1", response.getBody().get(0).getTitle());

        verify(expenseService, times(1)).getExpensesByUserAndCompany(1L, 1L);
    }

    @Test
    void testGetExpensesByDateRange(){
        // Given
        List<Expense> expenseList = Arrays.asList(expense1, expense2);
        when(expenseService.getExpensesByDateRange(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(expenseList);

        // When
        ResponseEntity<List<Expense>> response = expenseController.getExpensesByDateRange(LocalDateTime.now(), LocalDateTime.now().plusDays(7));

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals("Test Title 1", response.getBody().get(0).getTitle());

        verify(expenseService, times(1)).getExpensesByDateRange(any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void testUpdateExpense(){
        // Given
        when(expenseService.updateExpense(1L, expense1)).thenReturn(expense1);

        // When
        ResponseEntity<Expense> response = expenseController.updateExpense(1L, expense1);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Test Title 1", response.getBody().getTitle());

        verify(expenseService, times(1)).updateExpense(1L, expense1);
    }

    @Test
    void testDeleteExpense(){
        // Given
        doNothing().when(expenseService).deleteExpense(1L);

        // When & Then
        expenseController.deleteExpense(1L);
        verify(expenseService, times(1)).deleteExpense(1L);
    }

    @Test
    void testMarkExpenseAsPaid(){
        // Given
        doNothing().when(expenseService).markAsPaid(1L);

        // When
        ResponseEntity<String> response = expenseController.markExpenseAsPaid(1L);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Expense paid successfully!", response.getBody());

        verify(expenseService, times(1)).markAsPaid(1L);
    }

    @Test
    void testUpdateDueDate(){
        // Given
        doNothing().when(expenseService).updateDueDate(eq(1L), any(LocalDateTime.class));

        // When
        ResponseEntity<String> response = expenseController.updateDueDate(1L, LocalDateTime.now());

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Expense Due Date updated successfully!", response.getBody());

        verify(expenseService, times(1)).updateDueDate(eq(1L), any(LocalDateTime.class));
    }

    @Test
    void testGetPaidExpenses(){
        // Given
        List<Expense> expenseList = Arrays.asList(expense1, expense2);
        when(expenseService.getPaidExpenses()).thenReturn(expenseList);

        // When
        ResponseEntity<List<Expense>> response = expenseController.getPaidExpenses();

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals("Test Title 1", response.getBody().get(0).getTitle());

        verify(expenseService, times(1)).getPaidExpenses();
    }

    @Test
    void testGetUnpaidDueExpensesForToday(){
        // Given
        List<Expense> expenseList = Arrays.asList(expense1, expense2);
        when(expenseService.getUnpaidDueExpensesForToday()).thenReturn(expenseList);

        // When
        ResponseEntity<List<Expense>> response = expenseController.getUnpaidDueExpensesForToday();

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals("Test Title 1", response.getBody().get(0).getTitle());

        verify(expenseService, times(1)).getUnpaidDueExpensesForToday();
    }

    @Test
    void testGetAllOverallUnpaidDueExpenses(){
        // Given
        List<Expense> expenseList = Arrays.asList(expense1, expense2);
        when(expenseService.getAllOverallUnpaidDueExpenses()).thenReturn(expenseList);

        // When
        ResponseEntity<List<Expense>> response = expenseController.getAllOverallUnpaidDueExpenses();

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals("Test Title 1", response.getBody().get(0).getTitle());

        verify(expenseService, times(1)).getAllOverallUnpaidDueExpenses();
    }
}
