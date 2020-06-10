package com.kropotov.asrd.services.springdatajpa.titles.company;

import com.kropotov.asrd.entities.company.CompanyPhone;
import com.kropotov.asrd.repositories.company.CompanyPhoneRepository;
import com.kropotov.asrd.services.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyPhoneService implements CrudService<CompanyPhone, Long> {

    private final CompanyPhoneRepository companyPhoneRepository;


    @Override
    public Optional<List<CompanyPhone>> getAll() {
            return Optional.ofNullable(companyPhoneRepository.findAll());
    }

    @Override
    public Optional<CompanyPhone> getById(Long id) {
        return companyPhoneRepository.findById(id);
    }

    @Transactional
    public CompanyPhone save(CompanyPhone companyPhone) {
        return companyPhoneRepository.save(companyPhone);
    }

    @Override
    public void deleteById(Long id) {
        companyPhoneRepository.deleteById(id);
    }

}
