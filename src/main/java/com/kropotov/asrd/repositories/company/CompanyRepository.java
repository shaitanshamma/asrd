package com.kropotov.asrd.repositories.company;

import com.kropotov.asrd.entities.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findOneByTitle(String title);
    List<Company> findByMilitaryRepresentation(String militaryRepresentation);
}
