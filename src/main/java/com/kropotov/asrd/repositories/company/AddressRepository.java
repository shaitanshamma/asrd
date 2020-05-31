package com.kropotov.asrd.repositories.company;

import com.kropotov.asrd.entities.company.Address;
import com.kropotov.asrd.entities.company.Company;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AddressRepository extends PagingAndSortingRepository<Address, Long> {
    List<Address> findAllByCompany(Company company);
    List<Address> findAll();
}
