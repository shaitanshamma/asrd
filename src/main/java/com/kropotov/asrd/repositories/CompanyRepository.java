package com.kropotov.asrd.repositories;

import com.kropotov.asrd.entities.CompanyOld;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends PagingAndSortingRepository<CompanyOld, Long> {
    CompanyOld findOneByTitle(String title);
}
