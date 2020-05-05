package com.kropotov.asrd.services.springdatajpa.titles;

import com.kropotov.asrd.entities.Company;
import com.kropotov.asrd.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public List<Company> getAll() {
        List<Company> companyList = new ArrayList<>();
        companyRepository.findAll().iterator().forEachRemaining(companyList::add);
        return companyList;
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public Company getByTitle(String title) {
        return companyRepository.findOneByTitle(title);
    }

    public Optional<Company> getById(Long id) {
        return companyRepository.findById(id);
    }
}
