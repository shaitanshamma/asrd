package com.kropotov.asrd.converters.company;

import com.kropotov.asrd.dto.company.CompanyDto;
import com.kropotov.asrd.entities.company.Company;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyToDto implements Converter<Company, CompanyDto> {

    @Nullable
    @Override
    public CompanyDto convert(@NonNull Company source) {

        return CompanyDto.builder()
                .id(source.getId())
                .title(source.getTitle())
                .email(source.getEmail())
                .fax(source.getFax())
                .militaryRepresentation(source.getMilitaryRepresentation())
                .build();
    }
}
