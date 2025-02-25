package com.expense.management.services;

import com.expense.management.controllers.ExpenseController;
import com.expense.management.models.Category;
import com.expense.management.models.Expense;
import com.expense.management.repositories.CategoryRepository;
import com.expense.management.repositories.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {
    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ExpenseService expenseService;

    @Test
    void testCreateExpense() {
        Expense expense = new Expense();
        expense.setCategory(new Category(1L, "Food", null, null));

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(new Category(1L, "Food", null, null)));
        when(expenseRepository.save(expense)).thenReturn(expense);

        Expense result = expenseService.createExpense(expense);
        assertNotNull(result);
        verify(expenseRepository).save(expense);
    }

    @Test
    void testGetAllExpenses() {
        when(expenseRepository.findAll()).thenReturn(List.of(new Expense(), new Expense()));
        List<Expense> expenses = expenseService.getAllExpenses();
        assertEquals(2, expenses.size());
    }

    @Test
    void testGetExpenseById() {
        Expense expense = new Expense();
        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));
        assertEquals(expense, expenseService.getExpenseById(1L));
    }

    @Test
    void testGetExpensesByUser() {
        when(expenseRepository.findByUserId(1L)).thenReturn(List.of(new Expense(), new Expense()));
        List<Expense> expenses = expenseService.getExpensesByUser(1L);
        assertEquals(2, expenses.size());
    }

    @Test
    void testGetExpensesByCompany() {
        when(expenseRepository.findByCompanyId(1L)).thenReturn(List.of(new Expense()));
        assertEquals(1, expenseService.getExpensesByCompany(1L).size());
    }

    @Test
    void testGetExpensesByUserAndCompany() {
        when(expenseRepository.findByUserIdAndCompanyId(1L, 2L)).thenReturn(List.of(new Expense()));
        assertEquals(1, expenseService.getExpensesByUserAndCompany(1L, 2L).size());
    }

    @Test
    void testUpdateExpense() {
        Expense expense = new Expense();
        Expense updatedExpense = new Expense();
        updatedExpense.setTitle("Updated");

        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));
        when(expenseRepository.save(any())).thenReturn(updatedExpense);

        Expense result = expenseService.updateExpense(1L, updatedExpense);
        assertEquals("Updated", result.getTitle());
    }

    @Test
    void testDeleteExpense() {
        when(expenseRepository.existsById(1L)).thenReturn(true);
        doNothing().when(expenseRepository).deleteById(1L);

        assertDoesNotThrow(() -> expenseService.deleteExpense(1L));
        verify(expenseRepository).deleteById(1L);
    }

    @Test
    void testGetUnpaidDueExpensesForToday() {
        when(expenseRepository.findUnpaidDueExpensesForToday(any(), any())).thenReturn(List.of(new Expense()));
        assertEquals(1, expenseService.getUnpaidDueExpensesForToday().size());
    }

    @Test
    void testGetAllOverallUnpaidDueExpenses() {
        when(expenseRepository.findAllOverallUnpaidDueExpenses(any())).thenReturn(List.of(new Expense()));
        assertEquals(1, expenseService.getAllOverallUnpaidDueExpenses().size());
    }

    @Test
    void testMarkAsPaid() {
        Expense expense = new Expense();
        expense.setPaid(false);

        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));

        expenseService.markAsPaid(1L);

        verify(expenseRepository).save(expense);
        assertTrue(expense.isPaid());
    }

    @Test
    void testUpdateDueDate() {
        Expense expense = new Expense();
        expense.setDueDate(LocalDateTime.now().minusDays(1));

        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));

        expenseService.updateDueDate(1L, LocalDateTime.now().plusDays(1));

        verify(expenseRepository).save(expense);
        assertFalse(expense.isPaid());
    }

    @Test
    void testGetPaidExpenses() {
        when(expenseRepository.findPaidExpenses()).thenReturn(List.of(new Expense()));
        assertEquals(1, expenseService.getPaidExpenses().size());
    }
}

