package com.kropotov.asrd.services;

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

    public List<Invoice> getAllInvoices() {
        return (List<Invoice>) (invoiceRepository.findAll());
    }
}
