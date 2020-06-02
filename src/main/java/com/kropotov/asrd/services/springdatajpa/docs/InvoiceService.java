package com.kropotov.asrd.services.springdatajpa.docs;

import com.kropotov.asrd.converters.docs.InvoiceToDto;
import com.kropotov.asrd.dto.docs.InvoiceDto;
import com.kropotov.asrd.entities.docs.Invoice;
import com.kropotov.asrd.repositories.InvoiceRepository;
import com.kropotov.asrd.services.CrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceService implements CrudService<Invoice, Long> {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceToDto invoiceToDto;


    public Page<Invoice> getAll(Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }

    @Override
    public Optional<List<Invoice>> getAll() {
        return Optional.ofNullable(invoiceRepository.findAll());
    }

    @Override
    public Optional<Invoice> getById(Long id) {
        return invoiceRepository.findById(id);
    }

    public boolean isInvoiceWithNumberExists(String number) {
        return invoiceRepository.findOneByNumber(number) != null;
    }

    @Transactional
    @Override
    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> findAllByNumberLike(String number) {
        return invoiceRepository.findAllByNumberLike(number);
    }

    public List<Invoice> findAllByNumber(String number) {
        return invoiceRepository.findAllByNumber(number);
    }

    @Transactional
    public InvoiceDto getDtoById(Long id) {

        Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);
        if (invoiceOptional.isPresent()) {
            return invoiceToDto.convert(invoiceOptional.get());
        } else {
            return new InvoiceDto();
        }
    }

    @Override
    public void deleteById(Long id) {
        invoiceRepository.deleteById(id);
    }
}
