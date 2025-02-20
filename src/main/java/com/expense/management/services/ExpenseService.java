package com.expense.management.services;

import com.expense.management.exceptions.ExpenseNotFoundException;
import com.expense.management.models.Expense;
import com.expense.management.repositories.ExpenseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository){
        this.expenseRepository= expenseRepository;
    }

    public Expense createExpense(Expense expense){
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
}
