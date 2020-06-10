package com.kropotov.asrd.services.springdatajpa.titles.company;

import com.kropotov.asrd.entities.company.Address;
import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.repositories.company.AddressRepository;
import com.kropotov.asrd.services.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService implements CrudService<Address, Long> {

    private final AddressRepository addressRepository;

    @Override
    public Optional<List<Address>> getAll() {
        return Optional.ofNullable(addressRepository.findAll());
    }

    @Override
    public Optional<Address> getById(Long id) {
        return addressRepository.findById(id);
    }


    @Transactional
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void deleteById(Long id) {
        addressRepository.deleteById(id);
    }

    public List<Address> getAllByCompany(Company company) {
        return addressRepository.findAllByCompany(company);
    }
}
