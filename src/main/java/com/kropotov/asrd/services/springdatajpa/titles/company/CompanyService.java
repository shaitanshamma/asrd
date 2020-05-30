package com.kropotov.asrd.services.springdatajpa.titles.company;

import com.kropotov.asrd.converters.CompanyToDto;
import com.kropotov.asrd.dto.CompanyDto;
import com.kropotov.asrd.dto.items.ControlSystemDto;
import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.entities.items.ControlSystem;
import com.kropotov.asrd.repositories.company.CompanyRepository;
import com.kropotov.asrd.services.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService implements CrudService<Company, Long> {

    private final CompanyRepository companyRepository;
    private final CompanyToDto companyToDto;

    public Optional<Company> getById(Long id){
        return companyRepository.findById(id);
    }

    public List<Company> getAll(){
        return companyRepository.findAll();
    }

    public Company getOneByTitle(String title){
        return companyRepository.findOneByTitle(title);
    }

    @Transactional
    public Company save(Company company){
        return companyRepository.save(company);
    }

    @Override
    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }

    public List<Company> getByMilitaryRepresentation(String militaryRepresentation){
        return companyRepository.findByMilitaryRepresentation(militaryRepresentation);
    }

    @Transactional
    public CompanyDto getDtoById(Long id) {
        return companyToDto.convert(getById(id).orElse(new Company()));
    }
}
