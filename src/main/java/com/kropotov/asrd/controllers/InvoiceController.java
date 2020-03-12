package com.kropotov.asrd.controllers;

import com.kropotov.asrd.entities.Invoice;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.services.CompanyService;
import com.kropotov.asrd.services.InvoiceService;
import com.kropotov.asrd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {
    private InvoiceService invoiceService;
    private CompanyService companyService;
    private UserService userService;

    @Autowired
    public void setInvoiceService(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Autowired
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String invoicePage(Model model) {
        List<Invoice> invoices = invoiceService.findAll();
        model.addAttribute("invoices", invoices);
        return "invoice-page";
    }

    @GetMapping("/{id}")
    public String edit(Model model, @PathVariable(name = "id") Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Invoice invoice = invoiceService.findById(id);
        if (invoice == null) {
            invoice = new Invoice();
            invoice.setId(0L);
        }
        model.addAttribute("invoice", invoice);
        model.addAttribute("companies", companyService.findAll());
        return "edit-invoice";
    }

    @PostMapping("")
    public String saveModifiedInvoice(@Valid @ModelAttribute("invoice") Invoice invoice, BindingResult bindingResult, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        invoice.setUser(userService.findByUserName(principal.getName()));
        if (invoice.getId() == 0 && invoiceService.isInvoiceWithNumberExists(invoice.getNumber())) {
            model.addAttribute("invoice", invoice);
            model.addAttribute("invoiceCreationError", "Накладная с таким номером уже существует");
            model.addAttribute("companies", companyService.findAll());
            return "edit-invoice";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("invoice", invoice);
            model.addAttribute("invoiceCreationError", "Ошибка валидатора");
            model.addAttribute("companies", companyService.findAll());
        }

        //TODO убрать заглушку
        //invoice.setDate(LocalDate.now());
        //invoice.setCreatedAt(LocalDateTime.now());
        //invoice.setUpdatedAt(LocalDateTime.now());
        invoice.setPath("/path");
        invoice.setEntityStatus("active");
        invoiceService.saveOrUpdate(invoice);
        return "redirect:/invoice/";
    }
}
