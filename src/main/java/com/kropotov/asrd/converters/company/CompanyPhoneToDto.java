package com.kropotov.asrd.converters.company;

import com.kropotov.asrd.dto.company.CompanyPhoneDto;
import com.kropotov.asrd.entities.company.CompanyPhone;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


@Component
@RequiredArgsConstructor
public class CompanyPhoneToDto implements Converter<CompanyPhone, CompanyPhoneDto> {

    @Nullable
    @Override
    public CompanyPhoneDto convert(@NonNull CompanyPhone phone) {
        Assert.notNull(phone, "Объект phone не может быть пустым");
        return CompanyPhoneDto.builder()
                .id(phone.getId())
                .phone(phone.getPhone())
                .description(phone.getDescription())
                .build();
    }
}
