package com.kropotov.asrd.services.springdatajpa.titles.company;

import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.entities.company.CompanyPhone;
import com.kropotov.asrd.repositories.company.CompanyPhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyPhoneService {
    @Autowired
    private CompanyPhoneRepository companyPhoneRepository;

    public List<CompanyPhone> findAll(){
        return companyPhoneRepository.findAll();
    }

    public List<CompanyPhone> findAllByCompany(Company company){
        return companyPhoneRepository.findAllByCompany(company);
    }
    public CompanyPhone save(CompanyPhone companyPhone){
        return companyPhoneRepository.save(companyPhone);
    }

    public void deleteById(Long id){
        companyPhoneRepository.deleteById(id);
    }
}
