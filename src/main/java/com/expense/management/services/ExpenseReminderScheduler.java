package com.expense.management.services;

import com.expense.management.models.Expense;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseReminderScheduler {

    private final ExpenseService expenseService;
    private final ExpenseReminderProducer producer;

    @Scheduled(cron = "0 0 8 * * ?")  // Runs every day at 8 AM
    public void scheduleReminder(){
        List<Expense> dueExpenses = expenseService.getUnpaidDueExpensesForToday();
        for(Expense expense : dueExpenses){
            producer.sendReminder(expense.getId(), expense.getUser().getEmail());
        }
    }
}
