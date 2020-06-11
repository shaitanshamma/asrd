package com.kropotov.asrd.converters.company;

import com.kropotov.asrd.dto.company.CompanyDto;

import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.entities.company.Employee;
import com.kropotov.asrd.services.springdatajpa.titles.company.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DtoToCompany implements Converter<CompanyDto, Company> {

    private final CompanyService companyService;

    @Nullable
    @Override
    public Company convert(@NonNull CompanyDto companyDto) {

        if (companyDto.getId() == null) {
            Company company = new Company();
            company.setTitle(companyDto.getTitle());
            company.setMilitaryRepresentation(companyDto.getMilitaryRepresentation());
            company.setEmail(companyDto.getEmail());
            company.setFax(companyDto.getFax());
            return company;
        }
        Optional<Company> company = companyService.getById(companyDto.getId());
        company.get().setTitle(companyDto.getTitle());
        company.get().setMilitaryRepresentation(companyDto.getMilitaryRepresentation());
        company.get().setEmail(companyDto.getEmail());
        company.get().setFax(companyDto.getFax());
        return company.get();
    }
}
