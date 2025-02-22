package com.expense.management.controllers;

import com.expense.management.models.Expense;
import com.expense.management.services.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService){
        this.expenseService= expenseService;
    }

    @PostMapping
    public ResponseEntity<String> createExpense(@Valid @RequestBody Expense expense){
        expenseService.createExpense(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body("Expense created successfully!");
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses(){
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id){
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Expense>> getExpensesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(expenseService.getExpensesByUser(userId));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Expense>> getExpensesByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(expenseService.getExpensesByCompany(companyId));
    }

    @GetMapping("/user/{userId}/company/{companyId}")
    public ResponseEntity<List<Expense>> getExpensesByUserAndCompany(@PathVariable Long userId, @PathVariable Long companyId){
        return ResponseEntity.ok(expenseService.getExpensesByUserAndCompany(userId, companyId));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Expense>> getExpensesByDateRange(@RequestParam("startDate")LocalDateTime startDate, @RequestParam("endDate") LocalDateTime endDate){
        return ResponseEntity.ok(expenseService.getExpensesByDateRange(startDate,endDate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense updatedExpense) {
        return ResponseEntity.ok(expenseService.updateExpense(id, updatedExpense));
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id){
        expenseService.deleteExpense(id);
    }

    @PatchMapping("/{id}/mark-paid")
    public ResponseEntity<String> markExpenseAsPaid(@PathVariable Long id){
        expenseService.markAsPaid(id);
        return ResponseEntity.status(HttpStatus.OK).body("Expense paid successfully!");
    }

    @PatchMapping("/{id}/update-due-date")
    public ResponseEntity<String> updateDueDate(@PathVariable Long id, @RequestBody LocalDateTime newDueDate){
        expenseService.updateDueDate(id,newDueDate);
        return ResponseEntity.status(HttpStatus.OK).body("Expense Due Date updated successfully!");
    }

    @GetMapping("/paid")
    public ResponseEntity<List<Expense>> getPaidExpenses(){
        return ResponseEntity.ok(expenseService.getPaidExpenses());
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Expense>> getUnpaidDueExpensesForToday(){
        return ResponseEntity.ok(expenseService.getUnpaidDueExpensesForToday());
    }

    @GetMapping("/overdue/all")
    public ResponseEntity<List<Expense>> getAllOverallUnpaidDueExpenses(){
        return ResponseEntity.ok(expenseService.getAllOverallUnpaidDueExpenses());
    }
}
