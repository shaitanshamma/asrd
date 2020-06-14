package com.kropotov.asrd.converters.company;

import com.kropotov.asrd.dto.company.CompanyPhoneDto;
import com.kropotov.asrd.entities.company.CompanyPhone;
import com.kropotov.asrd.services.springdatajpa.titles.company.CompanyPhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DtoCompanyPhoneToCompanyPhone implements Converter<CompanyPhoneDto, CompanyPhone> {

    private final CompanyPhoneService companyPhoneService;

    @Nullable
    @Override
    public CompanyPhone convert(@NonNull CompanyPhoneDto phoneDto) {

        Assert.notNull(phoneDto, "Объект phoneDto не может быть пустым");

        if (phoneDto.getId() == null) {
            final CompanyPhone companyPhone = new CompanyPhone();
            companyPhone.setPhone(phoneDto.getPhone());
            companyPhone.setDescription(phoneDto.getDescription());
            return companyPhone;
        }
        Optional<CompanyPhone> companyPhone = companyPhoneService.getById(phoneDto.getId());
        companyPhone.get().setDescription(phoneDto.getDescription());
        companyPhone.get().setPhone(phoneDto.getPhone());
        return companyPhone.get();
    }
}
