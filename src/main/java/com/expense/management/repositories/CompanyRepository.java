package com.expense.management.repositories;

import com.expense.management.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {

    Optional<Company> findByName(String companyName);

    Boolean existsByName(String companyName);
}
