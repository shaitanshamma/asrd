package com.kropotov.asrd.api.controllers;

import com.kropotov.asrd.converters.docs.InvoiceToDto;
import com.kropotov.asrd.dto.docs.InvoiceDto;
import com.kropotov.asrd.entities.docs.Invoice;
import com.kropotov.asrd.services.springdatajpa.docs.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceApiController {

    private final InvoiceService invoiceService;
    private final InvoiceToDto invoiceToDto;

    @GetMapping("{id}")
    public Invoice getInvoiceById(@PathVariable("id") Long id) {
        return invoiceService.findById(id);
    }

    @GetMapping("/like")
    public List<InvoiceDto> findInvoiceByNumberLike(@RequestParam(value = "invoiceNumber") String invoiceNumber) {
        if (!StringUtils.hasLength(invoiceNumber)) {
            return null;
        }
        List<InvoiceDto> results = new ArrayList<>();
        invoiceService.findAllByNumberLike(invoiceNumber + "%").forEach(invoice -> results.add(invoiceToDto.convert(invoice)));
        return results;
    }

    @GetMapping
    public List<Invoice> findInvoiceByNumber(@RequestParam("invoiceNumber") String invoiceNumber) {
        if (!StringUtils.hasLength(invoiceNumber)) {
            return null;
        }
        List<Invoice> results = invoiceService.findAllByNumber(invoiceNumber);
        return results;
    }

}
