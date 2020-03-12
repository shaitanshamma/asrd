package com.kropotov.asrd.services;

import com.kropotov.asrd.entities.Company;
import com.kropotov.asrd.entities.Invoice;
import com.kropotov.asrd.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    private InvoiceRepository invoiceRepository;

    @Autowired
    public void setInvoiceRepository(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<Invoice> findAll() {
        return (List<Invoice>) (invoiceRepository.findAll());
    }

    public Invoice findById(Long id) { return invoiceRepository.findById(id).orElse(null); }

    public boolean isInvoiceWithNumberExists(String number) {
        return invoiceRepository.findOneByNumber(number) != null;
    }

    public Invoice saveOrUpdate(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }
}
