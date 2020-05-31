package com.kropotov.asrd.repositories.company;

import com.kropotov.asrd.entities.company.Company;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {
    Company findOneByTitle(String title);
    List<Company> findByMilitaryRepresentation(String militaryRepresentation);
    List<Company> findAll();
}
