package com.expense.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity  // This enables @PreAuthorize globally
@EnableScheduling    // Enables @Scheduled tasks (ExpenseReminderScheduler)
@EnableCaching  // Enables Spring Caching
public class ExpenseManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseManagementApplication.class, args);
	}

}
