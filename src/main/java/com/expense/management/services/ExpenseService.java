package com.expense.management.services;

import com.expense.management.exceptions.ExpenseNotFoundException;
import com.expense.management.models.Expense;
import com.expense.management.repositories.ExpenseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository){
        this.expenseRepository= expenseRepository;
    }

    public List<Expense> getAllExpenses(){
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long id){
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense with ID "+id+" not found"));
    }

    public Expense saveExpense(Expense expense){
        return expenseRepository.save(expense);
    }

    @Transactional
    public void deleteExpense(Long id){
        if(!expenseRepository.existsById(id)){
            throw new ExpenseNotFoundException("Expense with ID "+id+" not found");
        }
        expenseRepository.deleteById(id);
    }
}
