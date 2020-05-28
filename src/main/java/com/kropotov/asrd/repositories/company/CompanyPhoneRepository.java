package com.kropotov.asrd.repositories.company;

import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.entities.company.CompanyPhone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyPhoneRepository extends JpaRepository<CompanyPhone, Long> {
    List<CompanyPhone> findAllByCompany(Company company);
}
