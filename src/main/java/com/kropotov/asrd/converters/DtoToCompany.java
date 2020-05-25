package com.kropotov.asrd.converters;

import com.kropotov.asrd.dto.CompanyDto;
import com.kropotov.asrd.entities.CompanyOld;
import com.kropotov.asrd.services.springdatajpa.titles.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DtoToCompany implements Converter<CompanyDto, CompanyOld> {

    private final CompanyService companyService;

    @Synchronized
    @Nullable
    @Override
    public CompanyOld convert(@NonNull CompanyDto source) {
        if (source == null) {
            return null;
        }
        return companyService.getById(source.getId()).orElseThrow(() -> new RuntimeException("Нет тако компании"));
    }
}
