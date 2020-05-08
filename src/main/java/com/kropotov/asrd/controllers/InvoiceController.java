package com.kropotov.asrd.controllers;

import com.kropotov.asrd.converters.UserToSimple;
import com.kropotov.asrd.converters.docs.DtoToInvoice;
import com.kropotov.asrd.dto.docs.InvoiceDto;
import com.kropotov.asrd.dto.items.SimpleControlSystem;
import com.kropotov.asrd.dto.items.SimpleDevice;
import com.kropotov.asrd.entities.docs.Invoice;
import com.kropotov.asrd.exceptions.StorageException;
import com.kropotov.asrd.services.StorageService;
import com.kropotov.asrd.services.UserService;
import com.kropotov.asrd.services.springdatajpa.docs.InvoiceService;
import com.kropotov.asrd.services.springdatajpa.titles.CompanyService;
import com.kropotov.asrd.services.springdatajpa.titles.DeviceTitleService;
import com.kropotov.asrd.services.springdatajpa.titles.SystemTitleService;
import com.kropotov.asrd.services.springdatajpa.titles.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

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
    private final StorageService storageService;
    private final UserToSimple userToSimple;

    private final DtoToInvoice dtoToInvoice;

    private static final String INVOICE_CREATE_OR_UPDATE_FORM = "invoices/edit-invoice";

    @GetMapping
    public String invoicePage(Model model) {
        List<Invoice> invoices = invoiceService.getAll();
        model.addAttribute("invoices", invoices);
        return "invoices/list-invoices";
    }

    @GetMapping("/{id}/show")
    public String displayById(@PathVariable Long id, Model model) {
        Optional<Invoice> invoiceOptional = invoiceService.getById(id);
        if (invoiceOptional.isPresent()) {
            model.addAttribute("invoice", invoiceOptional.get());
            return "invoices/show";
        } else {
            return "redirect:/invoices";
        }

    }

    @GetMapping("/{id}/update")
    public String edit(Model model, @PathVariable(name = "id") Long id, Principal principal, HttpServletRequest request) {
        if (principal == null) {
            return "redirect:/login";
        }
        InvoiceDto invoiceDto = invoiceService.getDtoById(id);

        HttpSession session = request.getSession();
        session.setAttribute("invoice", invoiceDto);


        model.addAttribute("invoice", invoiceDto);
        model.addAttribute("topicTitleList", topicService.getAll());
        model.addAttribute("companies", companyService.getAll());
        return INVOICE_CREATE_OR_UPDATE_FORM;
    }

    //todo сделать рефакторинг

    @PostMapping("/add/device")
    public String addDeviceDto(@ModelAttribute("invoice") InvoiceDto invoiceDto,
                               @RequestParam("titleId") Long titleId, @RequestParam("itemNumber") String itemNumber,
                               Model model, HttpServletRequest request) {

        InvoiceDto sessionInvoice = (InvoiceDto) request.getSession().getAttribute("invoice");

        sessionInvoice.addDevice(SimpleDevice.builder().deviceTitle(deviceTitleService.getById(titleId).get()).number(itemNumber).build());

        // todo убрать везде один и тот же код
        invoiceDto.setDevices(sessionInvoice.getDevices());
        invoiceDto.setSystems(sessionInvoice.getSystems());

        model.addAttribute("invoice", invoiceDto);
        model.addAttribute("topicTitleList", topicService.getAll());
        model.addAttribute("companies", companyService.getAll());
        return INVOICE_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/add/system")
    public String addSystemDto(@ModelAttribute("invoice") InvoiceDto invoiceDto,
                               @RequestParam("titleId") Long titleId, @RequestParam("itemNumber") String itemNumber,
                               Model model, HttpServletRequest request) {

        InvoiceDto sessionInvoice = (InvoiceDto) request.getSession().getAttribute("invoice");

        sessionInvoice.addSystem((SimpleControlSystem.builder().systemTitle(systemTitleService.getById(titleId).get()).number(itemNumber).build()));

        // todo убрать везде один и тот же код
        invoiceDto.setDevices(sessionInvoice.getDevices());
        invoiceDto.setSystems(sessionInvoice.getSystems());

        model.addAttribute("invoice", invoiceDto);
        model.addAttribute("topicTitleList", topicService.getAll());
        model.addAttribute("companies", companyService.getAll());
        return INVOICE_CREATE_OR_UPDATE_FORM;
    }

    @GetMapping("/{invoiceId}/system/{listIndex}/delete")
    public String deleteSystemDtoById(@PathVariable("invoiceId") String invoiceId,
                                      @PathVariable("listIndex") int listIndex, Model model, HttpServletRequest request) {

        InvoiceDto sessionInvoice = (InvoiceDto) request.getSession().getAttribute("invoice");
        sessionInvoice.getSystems().remove(listIndex);
        return "redirect:/invoices/" + (invoiceId.equals("null") ? 0 : invoiceId) + "/update";
    }

    @GetMapping("/{invoiceId}/device/{listIndex}/delete")
    public String deleteDeviceDtoById(@PathVariable("invoiceId") String invoiceId,
                                      @PathVariable("listIndex") int listIndex, Model model, HttpServletRequest request) {

        InvoiceDto sessionInvoice = (InvoiceDto) request.getSession().getAttribute("invoice");
        sessionInvoice.getDevices().remove(listIndex);
        return "redirect:/invoices/" + (invoiceId.equals("null") ? 0 : invoiceId) + "/update";
    }

    // todo добавить проверку с какой страницы пришел, НЕЛЬЗЯ ДОПУСТИТЬ ЦИКЛИЧНОГО УДАЛЕНИЯ ВСЕХ ФАЙЛОВ ПО ID!!!!!!!
    // todo Перенести удаление файлов в FileController
    @GetMapping("/{invoiceId}/file/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteInvoiceFileById(@PathVariable("invoiceId") Long invoiceId) {
        Optional<Invoice> invoiceOptional = invoiceService.getById(invoiceId);
        try {
            if (invoiceOptional.isPresent()) {
                storageService.delete("invoices", invoiceOptional.get().getPath());
                invoiceOptional.get().setPath(null);
                invoiceService.save(invoiceOptional.get());
            }
        } catch (StorageException e) {
        }
    }

    @PostMapping("/edit")
    public String saveModifiedInvoice(@ModelAttribute("invoice") InvoiceDto invoiceDto,
                                      BindingResult bindingResult,
                                      Model model, HttpServletRequest request, Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        if (invoiceDto.getId() == null && invoiceService.isInvoiceWithNumberExists(invoiceDto.getNumber())) {
            model.addAttribute("invoice", invoiceDto);
            model.addAttribute("invoiceCreationError", "Накладная с таким номером уже существует");
            model.addAttribute("companies", companyService.getAll());
            return INVOICE_CREATE_OR_UPDATE_FORM;
        }

        InvoiceDto sessionInvoice = (InvoiceDto) request.getSession().getAttribute("invoice");
        invoiceDto.setDevices(sessionInvoice.getDevices());
        invoiceDto.setSystems(sessionInvoice.getSystems());

        try {
            invoiceDto.setUser(userToSimple.convert(userService.findByUserName(principal.getName())));
            invoiceService.save(dtoToInvoice.convert(invoiceDto));
            request.getSession().setAttribute("invoice", null);
        } catch (DateTimeParseException e) {
            // TODO
            model.addAttribute("deviceTitles", deviceTitleService.getAll());
            model.addAttribute("deviceCreationError", "Неверный формат даты");
            return INVOICE_CREATE_OR_UPDATE_FORM;
        }
        return "redirect:/invoices";
    }

}
