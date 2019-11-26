package com.kropotov.asrd.repositories;

import com.kropotov.asrd.entities.Company;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends PagingAndSortingRepository<Company, Long> {
    Company findOneByTitle(String title);
}
