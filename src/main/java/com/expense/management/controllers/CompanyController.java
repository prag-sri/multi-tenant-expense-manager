package com.expense.management.controllers;

import com.expense.management.exceptions.CompanyAlreadyExistsException;
import com.expense.management.models.Company;
import com.expense.management.models.Role;
import com.expense.management.services.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService){
        this.companyService= companyService;
    }

    @PostMapping
    public ResponseEntity<String> createCompany(@Valid @RequestBody Company company){
        companyService.createCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body("Company created successfully!");
    }

    @GetMapping
    public List<Company> getAllCompanies(){
        return companyService.getAllCompanies();
    }
}
