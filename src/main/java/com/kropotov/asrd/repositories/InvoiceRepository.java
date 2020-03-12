package com.kropotov.asrd.repositories;

import com.kropotov.asrd.entities.Invoice;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends PagingAndSortingRepository<Invoice, Long> {
    Invoice findOneByNumber(String number);
}
