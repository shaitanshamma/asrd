package com.kropotov.asrd.repositories.company;

import com.kropotov.asrd.entities.company.Company;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {
    Optional<Company> findOneByTitle(String title);
    List<Company> findByMilitaryRepresentation(String militaryRepresentation);
    List<Company> findAll();
}
