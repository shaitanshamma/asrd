package com.kropotov.asrd.converters.company;

import com.kropotov.asrd.dto.company.CompanyDto;

import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.services.springdatajpa.titles.company.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DtoToCompany implements Converter<CompanyDto, Company> {

    private final CompanyService companyService;

    @Synchronized
    @Nullable
    @Override
    public Company convert(@NonNull CompanyDto source) {
        if (source == null) {
            return null;
        }
        return companyService.getById(source.getId()).orElseThrow(() -> new RuntimeException("Нет тако компании"));
    }
}
