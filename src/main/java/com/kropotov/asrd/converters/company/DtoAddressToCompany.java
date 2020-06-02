package com.kropotov.asrd.converters.company;

import com.kropotov.asrd.dto.company.AddressDto;
import com.kropotov.asrd.entities.company.Address;
import com.kropotov.asrd.services.springdatajpa.titles.company.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DtoAddressToCompany implements Converter<AddressDto, Address> {

    private final AddressService addressService;

    @Nullable
    @Override
    public Address convert(@NonNull AddressDto addressDto) {
        if (addressDto == null) {
            return null;
        }
        Optional<Address> address = addressService.getById(addressDto.getId());
        address.get().setCity(addressDto.getCity());
        address.get().setDescription(addressDto.getDescription());
        address.get().setPlace(addressDto.getPlace());
        address.get().setZipCode(addressDto.getZipCode());
        address.get().setStreet(addressDto.getStreet());
        return address.get();
    }
}
