package com.kropotov.asrd.services.springdatajpa.titles.company;

import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.repositories.company.CompaniesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompaniesService {

    @Autowired
    private CompaniesRepository companiesRepository;

    public Optional<Company> findById(Long id){
        return companiesRepository.findById(id);
    }

    public List<Company> findAll(){
        return companiesRepository.findAll();
    }

    public Company findOneByTitle(String title){
        return companiesRepository.findOneByTitle(title);
    }

    public Company save(Company company){
        return companiesRepository.save(company);
    }

    public void deleteById(Long id){
        companiesRepository.deleteById(id);
    }

    public List<Company> findByMilitaryRepresentation(String militaryRepresentation){
        return companiesRepository.findByMilitaryRepresentation(militaryRepresentation);
    }
}
