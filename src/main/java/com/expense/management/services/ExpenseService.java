package com.expense.management.services;

import com.expense.management.exceptions.CategoryNotFoundException;
import com.expense.management.exceptions.ExpenseNotFoundException;
import com.expense.management.models.Category;
import com.expense.management.models.Expense;
import com.expense.management.repositories.CategoryRepository;
import com.expense.management.repositories.ExpenseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseService(ExpenseRepository expenseRepository, CategoryRepository categoryRepository){
        this.expenseRepository= expenseRepository;
        this.categoryRepository= categoryRepository;
    }

    public Expense createExpense(Expense expense){
        Long categoryId = expense.getCategory().getId();

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found!"));

        expense.setCategory(category);
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses(){
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long id){
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense with ID "+id+" not found"));
    }

    public List<Expense> getExpensesByUser(Long userId){
        return expenseRepository.findByUserId(userId);
    }

    public List<Expense> getExpensesByCompany(Long companyId){
        return expenseRepository.findByCompanyId(companyId);
    }

    public List<Expense> getExpensesByUserAndCompany(Long userId, Long companyId){
        return expenseRepository.findByUserIdAndCompanyId(userId, companyId);
    }

    public List<Expense> getExpensesByDateRange(LocalDateTime startDate, LocalDateTime endDate){
        return expenseRepository.findByDateBetween(startDate, endDate);
    }

    public Expense updateExpense(Long id, Expense updatedExpense){
        return expenseRepository.findById(id).map(expense -> {
            expense.setTitle(updatedExpense.getTitle());
            expense.setDescription(updatedExpense.getDescription());
            expense.setAmount(updatedExpense.getAmount());
            expense.setDate(updatedExpense.getDate());
            expense.setPaid(updatedExpense.isPaid());
            expense.setDueDate(updatedExpense.getDueDate());
            return expenseRepository.save(expense);
        }).orElseThrow(() -> new RuntimeException("Expense not found!"));
    }

    @Transactional
    public void deleteExpense(Long id){
        if(!expenseRepository.existsById(id)){
            throw new ExpenseNotFoundException("Expense with ID "+id+" not found");
        }
        expenseRepository.deleteById(id);
    }

    public List<Expense> getUnpaidDueExpensesForToday(){
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        return expenseRepository.findUnpaidDueExpensesForToday(startOfDay,endOfDay);
    }

    public List<Expense> getAllOverallUnpaidDueExpenses(){
        LocalDateTime today = LocalDateTime.now();
        return expenseRepository.findAllOverallUnpaidDueExpenses(today);
    }

    public void markAsPaid(Long id){
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense with ID "+id+" not found"));
        expense.setPaid(true);
        expenseRepository.save(expense);
    }

    public void updateDueDate(Long id, LocalDateTime newDueDate){
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense with ID "+id+" not found"));
        expense.setDueDate(newDueDate);

        // Update 'paid' as false if its due date falls after today
        if(newDueDate.isBefore(LocalDateTime.now())){
            expense.setPaid(false);
        }

        expenseRepository.save(expense);
    }

    public List<Expense> getPaidExpenses() {
        return expenseRepository.findPaidExpenses();
    }
}
