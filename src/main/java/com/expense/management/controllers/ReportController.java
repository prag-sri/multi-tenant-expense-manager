package com.expense.management.controllers;

import com.expense.management.models.Expense;
import com.expense.management.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/summary")
    public ResponseEntity<Map<String, BigDecimal>> getExpenseSummary(@AuthenticationPrincipal UserDetails user){
        Long companyId = getUserCompanyId(user);
        return ResponseEntity.ok(reportService.getExpenseSummary(companyId));
    }

    @GetMapping("/daily")
    public ResponseEntity<List<Map<String,Object>>> getDailyExpenses(
            @AuthenticationPrincipal UserDetails user,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        Long companyId = getUserCompanyId(user);
        return ResponseEntity.ok(reportService.getDailyExpenses(companyId,startDate,endDate));
    }

    @GetMapping("/top-expenses")
    public ResponseEntity<List<Expense>> getTopExpenses(
            @AuthenticationPrincipal UserDetails user,
            @RequestParam(defaultValue = "5") int limit){

        Long companyId = getUserCompanyId(user);
        return ResponseEntity.ok(reportService.getTopExpenses(companyId,limit));
    }

    private Long getUserCompanyId(UserDetails user){
        return ((com.expense.management.models.User) user).getCompany().getId();
    }
}
