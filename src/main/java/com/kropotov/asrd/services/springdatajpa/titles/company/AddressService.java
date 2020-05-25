package com.kropotov.asrd.services.springdatajpa.titles.company;

import com.kropotov.asrd.entities.company.Address;
import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.repositories.company.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public Optional<Address> getById(Long id){
        return addressRepository.findById(id);
    }
    public List<Address> getAll(){
        return addressRepository.findAll();
    }
    public Address save(Address address){
        return addressRepository.save(address);
    }
    public void delete(Long id){
        addressRepository.deleteById(id);
    }

    public List<Address> getAllByCompany(Company company) {
        return addressRepository.findAllByCompany(company);
    }
}
