package com.expense.management.services;

import com.expense.management.exceptions.CompanyAlreadyExistsException;
import com.expense.management.models.Company;
import com.expense.management.repositories.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    private Company company1;
    private Company company2;

    @BeforeEach
    void setup(){
        company1 = new Company(1L, "Company1");
        company2= new Company(2L, "Company2");
    }

    @Test
    void testCreateCompany_Success(){
        // Given
        when(companyRepository.existsByName(company1.getName())).thenReturn(false);
        when(companyRepository.save(company1)).thenReturn(company1);

        // When
        companyService.createCompany(company1);

        // Then
        verify(companyRepository, times(1)).save(company1);
    }

    @Test
    void testCreateCompany_Failure_CompanyAlreadyExists(){
        // Given
        when(companyRepository.existsByName(company1.getName())).thenReturn(true);

        // When
        assertThrows(CompanyAlreadyExistsException.class, () -> companyService.createCompany(company1));

        // Then
        verify(companyRepository, never()).save(any(Company.class));
    }

    @Test
    void testGetAllCompanies(){
        // Given
        List<Company> companies = Arrays.asList(company1, company2);
        when(companyRepository.findAll()).thenReturn(companies);

        // When
        List<Company> response = companyService.getAllCompanies();

        // Then
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Company2", response.get(1).getName());

        verify(companyRepository, times(1)).findAll();
    }
}
