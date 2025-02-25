package com.expense.management;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest(classes = ExpenseManagementApplication.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.expense.management")
class ExpenseManagementApplicationTests {

	@Test
	void contextLoads() {
	}

}
