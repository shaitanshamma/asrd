package com.kropotov.asrd.repositories.company;

import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.entities.company.CompanyPhone;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CompanyPhoneRepository extends PagingAndSortingRepository<CompanyPhone, Long> {
    List<CompanyPhone> findAllByCompany(Company company);
    List<CompanyPhone> findAll();
}
