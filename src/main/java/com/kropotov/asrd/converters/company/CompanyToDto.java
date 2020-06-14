package com.kropotov.asrd.converters.company;

import com.kropotov.asrd.dto.company.CompanyDto;
import com.kropotov.asrd.entities.company.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
public class CompanyToDto implements Converter<Company, CompanyDto> {

    @Nullable
    @Override
    public CompanyDto convert(@NonNull Company company) {
        Assert.notNull(company, "Объект company не может быть пустым");

        return CompanyDto.builder()
                .id(company.getId())
                .title(company.getTitle())
                .email(company.getEmail())
                .fax(company.getFax())
                .militaryRepresentation(company.getMilitaryRepresentation())
                .build();
    }
}
