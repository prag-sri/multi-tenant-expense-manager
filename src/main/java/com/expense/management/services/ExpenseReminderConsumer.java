package com.expense.management.services;

import com.expense.management.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExpenseReminderConsumer {
    private final EmailService emailService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void processReminder(Map<String,Object> message){
        String email = (String) message.get("email");
        Long expenseId =  message.get("expenseId") != null ? ((Number) message.get("expenseId")).longValue() : null;

        String subject = "Expense Payment Reminder";
        String body = "Your expense with ID " + expenseId + " is due. Please review it.";

        emailService.sendEmail(email, subject, body);
    }
}
