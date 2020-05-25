package com.kropotov.asrd.services.springdatajpa.titles.company;

import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.repositories.company.CompaniesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompaniesService {

    private final CompaniesRepository companiesRepository;

    public Optional<Company> getById(Long id){
        return companiesRepository.findById(id);
    }

    public List<Company> getAll(){
        return companiesRepository.findAll();
    }

    public Company getOneByTitle(String title){
        return companiesRepository.findOneByTitle(title);
    }

    public Company save(Company company){
        return companiesRepository.save(company);
    }

    public void delete(Long id){
        companiesRepository.deleteById(id);
    }

    public List<Company> getByMilitaryRepresentation(String militaryRepresentation){
        return companiesRepository.findByMilitaryRepresentation(militaryRepresentation);
    }
}
