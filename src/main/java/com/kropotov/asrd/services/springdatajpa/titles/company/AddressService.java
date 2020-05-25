package com.kropotov.asrd.services.springdatajpa.titles.company;

import com.kropotov.asrd.entities.company.Address;
import com.kropotov.asrd.entities.company.Company;
import com.kropotov.asrd.repositories.company.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Optional<Address> findOneById(Long id){
        return addressRepository.findById(id);
    }
    public List<Address> findAll(){
        return addressRepository.findAll();
    }
    public Address save(Address address){
        return addressRepository.save(address);
    }
    public void deleteById(Long id){
        addressRepository.deleteById(id);
    }

    public List<Address> findAllByCompany(Company company) {
        return addressRepository.findAllByCompany(company);
    }
}
