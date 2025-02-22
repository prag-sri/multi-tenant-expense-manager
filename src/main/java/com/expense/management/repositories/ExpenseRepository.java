package com.expense.management.repositories;

import com.expense.management.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {

    List<Expense> findByUserId(Long userId);
    List<Expense> findByCompanyId(Long companyId);
    List<Expense> findByUserIdAndCompanyId(Long userId, Long companyId);
    List<Expense> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Expense> findByCompanyIdAndDateBetween(Long companyId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT e FROM Expense e WHERE e.company.id = :companyId ORDER BY e.amount DESC LIMIT :limit")
    List<Expense> findByCompanyIdOrderByAmountDesc(Long companyId, int limit);

    @Query("SELECT e FROM Expense e WHERE e.dueDate BETWEEN :start AND :end AND e.paid = false")
    List<Expense> findUnpaidDueExpensesForToday(@Param("start") LocalDateTime startDate, @Param("end") LocalDateTime endTime);

    @Query("SELECT e FROM Expense e WHERE e.dueDate <= :today AND e.paid = false")
    List<Expense> findAllOverallUnpaidDueExpenses(@Param("today") LocalDateTime todayDate);

    @Query("SELECT e FROM Expense e WHERE e.paid = true")
    List<Expense> findPaidExpenses();
}
