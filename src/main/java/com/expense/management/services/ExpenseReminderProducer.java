package com.expense.management.services;

import com.expense.management.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExpenseReminderProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendReminder(Long expenseId, String userEmail) {
        Map<String, Object> message = new HashMap<>();
        message.put("expenseId", expenseId);
        message.put("email", userEmail);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, message);
    }
}
