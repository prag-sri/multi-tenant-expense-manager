package com.expense.management.controllers;

import com.expense.management.models.Company;
import com.expense.management.services.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanyControllerTest {

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyController companyController;

    private Company company1;
    private Company company2;

    @BeforeEach
    void setup(){
        company1 = new Company(1L, "Company1");
        company2= new Company(2L, "Company2");
    }

    @Test
    void testCreateCompany(){
        // Given
        doNothing().when(companyService).createCompany(any(Company.class));

        // When
        ResponseEntity<String> response = companyController.createCompany(company1);

        // Then
        assertEquals(201, response.getStatusCode().value());
        assertEquals("Company created successfully!", response.getBody());

        verify(companyService, times(1)).createCompany(company1);
    }

    @Test
    void testGetAllCompanies(){
        // Given
        List<Company> companies = Arrays.asList(company1, company2);
        when(companyService.getAllCompanies()).thenReturn(companies);

        // When
        List<Company> result = companyController.getAllCompanies();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Company1", result.get(0).getName());

        verify(companyService, times(1)).getAllCompanies();
    }
}
