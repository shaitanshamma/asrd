package com.kropotov.asrd.converters;

import com.kropotov.asrd.dto.CompanyDto;
import com.kropotov.asrd.entities.Company;
import com.kropotov.asrd.services.springdatajpa.titles.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyToDto implements Converter<Company, CompanyDto> {

    private final CompanyService companyService;

    @Synchronized
    @Nullable
    @Override
    public CompanyDto convert(@NonNull Company source) {
        if (source == null) {
            return null;
        }

        final CompanyDto companyDto = CompanyDto.builder()
                .id(source.getId())
                .title(source.getTitle())
            .build();

        return companyDto;
    }
}
