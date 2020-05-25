package com.kropotov.asrd.services.springdatajpa.titles;

import com.kropotov.asrd.entities.CompanyOld;
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

    public List<CompanyOld> getAll() {
        List<CompanyOld> companyOldList = new ArrayList<>();
        companyRepository.findAll().iterator().forEachRemaining(companyOldList::add);
        return companyOldList;
    }

    public CompanyOld save(CompanyOld companyOld) {
        return companyRepository.save(companyOld);
    }

    public CompanyOld getByTitle(String title) {
        return companyRepository.findOneByTitle(title);
    }

    public Optional<CompanyOld> getById(Long id) {
        return companyRepository.findById(id);
    }
}
