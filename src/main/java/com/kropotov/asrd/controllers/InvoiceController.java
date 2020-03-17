package com.kropotov.asrd.controllers;

import com.kropotov.asrd.entities.Invoice;
import com.kropotov.asrd.entities.Topic;
import com.kropotov.asrd.services.CompanyService;
import com.kropotov.asrd.services.InvoiceService;
import com.kropotov.asrd.services.TopicService;
import com.kropotov.asrd.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final CompanyService companyService;
    private final UserService userService;
    private final TopicService topicService;

    public InvoiceController(InvoiceService invoiceService, CompanyService companyService, UserService userService, TopicService topicService) {
        this.invoiceService = invoiceService;
        this.companyService = companyService;
        this.userService = userService;
        this.topicService = topicService;
    }

    @GetMapping
    public String invoicePage(Model model) {
        List<Invoice> invoices = invoiceService.findAll();
        model.addAttribute("invoices", invoices);
        return "invoices/list-invoices";
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
        if (invoice.getDate() != null) {
            model.addAttribute("strInvoiceDate", invoice.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        }

        Iterable<Topic> topicTitleList = topicService.getAll();
        model.addAttribute("topicTitleList", topicTitleList);
        model.addAttribute("itemNumber", new String());
        model.addAttribute("invoice", invoice);
        model.addAttribute("companies", companyService.findAll());
        return "invoices/edit-invoice";
    }

    @PostMapping("")
    public String saveModifiedInvoice(@Valid @ModelAttribute("invoice") Invoice invoice,
                                      @RequestParam("itemNumber") String itemNumber,
                                      @RequestParam("strInvoiceDate") String strInvoiceDate,
                                      BindingResult bindingResult,
                                      Model model,
                                      Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        invoice.setUser(userService.findByUserName(principal.getName()));
        if (invoice.getId() == 0 && invoiceService.isInvoiceWithNumberExists(invoice.getNumber())) {
            model.addAttribute("invoice", invoice);
            model.addAttribute("invoiceCreationError", "Накладная с таким номером уже существует");
            model.addAttribute("companies", companyService.findAll());
            return "invoices/edit-invoice";
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
        return "redirect:/invoices";
    }
}
