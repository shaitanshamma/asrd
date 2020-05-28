package com.kropotov.asrd.services.springdatajpa.titles.company;

import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.entities.company.CompanyPhone;
import com.kropotov.asrd.repositories.company.CompanyPhoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyPhoneService {

    private final CompanyPhoneRepository companyPhoneRepository;

    public List<CompanyPhone> getAll() {
        return companyPhoneRepository.findAll();
    }

    public List<CompanyPhone> getAllByCompany(Company company) {
        return companyPhoneRepository.findAllByCompany(company);
    }

    @Transactional
    public CompanyPhone save(CompanyPhone companyPhone) {
        return companyPhoneRepository.save(companyPhone);
    }

    public void delete(Long id) {
        companyPhoneRepository.deleteById(id);
    }
}
