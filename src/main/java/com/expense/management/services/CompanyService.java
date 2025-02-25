package com.expense.management.services;

import com.expense.management.exceptions.CompanyAlreadyExistsException;
import com.expense.management.models.Company;
import com.expense.management.repositories.CompanyRepository;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository= companyRepository;
    }

    public void createCompany(Company company){
        if(companyRepository.existsByName(company.getName())){
            throw new CompanyAlreadyExistsException("Company already exists!");
        }
        companyRepository.save(company);
    }

    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }
}
