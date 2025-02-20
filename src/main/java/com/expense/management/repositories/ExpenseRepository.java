package com.expense.management.repositories;

import com.expense.management.models.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
