package com.kropotov.asrd.services.springdatajpa.titles.company;

import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.repositories.company.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public Optional<Company> getById(Long id) {
        return companyRepository.findById(id);
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public Company getOneByTitle(String title) {
        return companyRepository.findOneByTitle(title);
    }

    @Transactional
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public void delete(Long id) {
        companyRepository.deleteById(id);
    }

    public List<Company> getByMilitaryRepresentation(String militaryRepresentation) {
        return companyRepository.findByMilitaryRepresentation(militaryRepresentation);
    }
}
