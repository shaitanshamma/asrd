package com.kropotov.asrd.controllers;

import com.kropotov.asrd.entities.ControlSystem;
import com.kropotov.asrd.entities.Device;
import com.kropotov.asrd.entities.Invoice;
import com.kropotov.asrd.entities.Topic;
import com.kropotov.asrd.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final CompanyService companyService;
    private final UserService userService;
    private final TopicService topicService;
    private final DeviceTitleService deviceTitleService;
    private final SystemTitleService systemTitleService;
    private final DeviceService deviceService;
    private final SystemService systemService;

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

    @PostMapping("/edit")
    public String saveModifiedInvoice(@Valid @ModelAttribute("invoice") Invoice invoice,
                                      @RequestParam("itemNumber") String itemNumber,
                                      @RequestParam("strInvoiceDate") String strInvoiceDate,
                                      @RequestParam("type") String type,
                                      @RequestParam("deviceTitle") Long deviceTitleId,
                                      @RequestParam("systemTitle") Long systemTitleId,
                                      BindingResult bindingResult,
                                      Model model,
                                      Principal principal) {


        // проверка, что пользователь залогинен
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

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        try {
            invoice.setDate(LocalDate.parse(strInvoiceDate, dateFormatter));
        } catch (DateTimeParseException e) {
            // TODO
            model.addAttribute("topicTitleList", topicService.getAll());
            model.addAttribute("invoiceCreationError", "Неверный формат даты");
            return "systems/edit-system";
        }

        if (type.equals("device")) {
            Device device = deviceService.getByNumberAndTitle(itemNumber, deviceTitleService.getById(deviceTitleId));
            if (device == null) {
                device = new Device();
                device.setTitle(deviceTitleService.getById(deviceTitleId));
                device.setNumber(itemNumber);
                device.setUser(userService.findByUserName(principal.getName()));
                deviceService.saveOrUpdate(device);
            }
            invoice.setDevices(Collections.singletonList(device));
        } else if (type.equals("system")) {
            ControlSystem system = systemService.getByNumberAndTitle(itemNumber, systemTitleService.getById(systemTitleId));
            if (system == null) {
                system = new ControlSystem();
                system.setTitle(systemTitleService.getById(systemTitleId));
                system.setNumber(itemNumber);
                system.setUser(userService.findByUserName(principal.getName()));
                systemService.saveOrUpdate(system);
            }
            invoice.setSystems(Collections.singletonList(system));
        }


        invoice.setPath("/path");
        invoice.setEntityStatus("active");
        invoiceService.saveOrUpdate(invoice);
        return "redirect:/invoices";
    }
}
