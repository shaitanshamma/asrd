package com.kropotov.asrd.converters.company;

import com.kropotov.asrd.dto.company.AddressDto;
import com.kropotov.asrd.entities.company.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressToDto implements Converter<Address, AddressDto> {

    @Nullable
    @Override
    public AddressDto convert(@NonNull Address address) {
        if (address == null) {
            return null;
        }
        final AddressDto addressDto = AddressDto.builder()
                .id(address.getId())
                .city(address.getCity())
                .street(address.getStreet())
                .place(address.getPlace())
                .zipCode(address.getZipCode())
                .description(address.getDescription())
                .build();
        return addressDto;
    }
}
