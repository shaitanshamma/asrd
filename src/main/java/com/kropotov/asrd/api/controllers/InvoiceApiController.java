package com.kropotov.asrd.api.controllers;

import com.kropotov.asrd.entities.docs.Invoice;
import com.kropotov.asrd.services.springdatajpa.docs.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceApiController {

    private final InvoiceService invoiceService;

    @GetMapping("{id}")
    public Invoice getInvoiceById(@PathVariable("id") Long id) {
        return invoiceService.findById(id);
    }

    @GetMapping("/like")
    public List<Invoice> findInvoiceByNumberLike(@RequestParam(value = "invoiceNumber") String invoiceNumber) {
        if (!StringUtils.hasLength(invoiceNumber)) {
            return null;
        }
        List<Invoice> results = invoiceService.findAllByNumberLike(invoiceNumber + "%");
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
