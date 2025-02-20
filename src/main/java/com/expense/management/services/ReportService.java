package com.expense.management.services;

import com.expense.management.models.Expense;
import com.expense.management.repositories.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ExpenseRepository expenseRepository;

    public ReportService(ExpenseRepository expenseRepository){
        this.expenseRepository = expenseRepository;
    }

    // Get total expenses grouped by category
    public Map<String, BigDecimal> getExpenseSummary(Long companyId){
        List<Expense> expenses = expenseRepository.findByCompanyId(companyId);

        return expenses.stream()
                .collect(Collectors.groupingBy(     // Collects the elements in the stream into a Map
                        e -> e.getCategory().getName(),    // Map's Key
                        Collectors.reducing(         // Map's value
                                BigDecimal.ZERO,    // Identity value (starting point for sum)
                                Expense::getAmount,   // Extracts the `amount` from each `Expense`
                                BigDecimal::add)  // Accumulates the amounts (sums them up)
                ));
    }

    // Get daily expense totals in a date range
    public List<Map<String,Object>> getDailyExpenses(Long companyId, LocalDate startDate, LocalDate endDate){
        // Convert LocalDate to LocalDateTime (start of day and end of day)
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59); // End of the day

        List<Expense> expenses = expenseRepository.findByCompanyIdAndDateBetween(companyId, startDateTime, endDateTime);

        return expenses.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getDate().toLocalDate(),  // Group by LocalDate
                        TreeMap::new,  // Preserve order by date
                        Collectors.reducing(BigDecimal.ZERO, Expense::getAmount, BigDecimal::add) // Sum amounts
                ))
                .entrySet().stream()
                .map(entry -> Map.of(   // Transforms the Grouped Data into a List of Maps
                                "date", (Object) entry.getKey(),  // Explicitly cast LocalDate to Object
                        "total", entry.getValue()  // BigDecimal is already Object
                ))
                .toList();
    }

    // Get top N highest expenses
    public List<Expense> getTopExpenses(Long companyId, int limit) {
        return expenseRepository.findByCompanyIdOrderByAmountDesc(companyId, limit);
    }
}
