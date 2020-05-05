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
import com.kropotov.asrd.services.springdatajpa.items.DeviceService;
import com.kropotov.asrd.services.springdatajpa.items.SystemService;
import com.kropotov.asrd.services.springdatajpa.titles.CompanyService;
import com.kropotov.asrd.services.springdatajpa.titles.DeviceTitleService;
import com.kropotov.asrd.services.springdatajpa.titles.SystemTitleService;
import com.kropotov.asrd.services.springdatajpa.titles.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.time.format.DateTimeParseException;
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
    private final StorageService storageService;
    private final UserToSimple userToSimple;

    private final DtoToInvoice dtoToInvoice;

    private static final String INVOICE_CREATE_OR_UPDATE_FORM = "invoices/edit-invoice";

    @GetMapping
    public String invoicePage(Model model) {
        List<Invoice> invoices = invoiceService.findAll();
        model.addAttribute("invoices", invoices);
        return "invoices/list-invoices";
    }

    @GetMapping("/{id}/show")
    public String displayById(@PathVariable Long id, Model model) {
        if (invoiceService.findById(id) != null) {
            model.addAttribute("invoice", invoiceService.findById(id));
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
        InvoiceDto sessionInvoice = (InvoiceDto) request.getSession().getAttribute("invoice");
        if (id == 0 && sessionInvoice != null) {
            invoiceDto = sessionInvoice;
        } else if (sessionInvoice == null) {
            HttpSession session = request.getSession();
            session.setAttribute("invoice", invoiceDto);
        }

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

        sessionInvoice.setNumber(invoiceDto.getNumber());
        sessionInvoice.setDate(invoiceDto.getDate());
        sessionInvoice.setFrom(invoiceDto.getFrom());
        sessionInvoice.setDestination(invoiceDto.getDestination());

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

        sessionInvoice.setNumber(invoiceDto.getNumber());
        sessionInvoice.setDate(invoiceDto.getDate());
        sessionInvoice.setFrom(invoiceDto.getFrom());
        sessionInvoice.setDestination(invoiceDto.getDestination());

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

    @PostMapping("/edit")
    public String saveModifiedInvoice(@ModelAttribute("invoice") InvoiceDto invoiceDto,
                                      @RequestParam(value = "file", required = false) MultipartFile file, //TODO после тестов сделать поле обязательным
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

/*        if (bindingResult.hasErrors()) {
            model.addAttribute("invoice", invoiceDto);
            model.addAttribute("invoiceCreationError", "Ошибка валидатора");
            model.addAttribute("companies", companyService.getAll());
        }*/

        InvoiceDto sessionInvoice = (InvoiceDto) request.getSession().getAttribute("invoice");
        invoiceDto.setDevices(sessionInvoice.getDevices());
        invoiceDto.setSystems(sessionInvoice.getSystems());

        //TODO сделать так, чтобы пути к файлам не были жестко зашиты в коде. Надо ли? Или сделать таблицу с индексацией имен?
        //String extension = file.getOriginalFilename().lastIndexOf('.')
        //String filename = "invoice_" + invoice.getNumber() + "_" + invoice.getDate() + "." + "pdf";
        try {
            storageService.store("invoices", file.getOriginalFilename(), file);
        } catch (StorageException e) {
            System.out.println(e.getMessage());
            /*model.addAttribute("invoice", invoice);
            model.addAttribute("invoiceCreationError", "Ошибка сохранения файла");
            model.addAttribute("companies", companyService.findAll());
            return INVOICE_CREATE_OR_UPDATE_FORM;*/
        }

        invoiceDto.setPath(file.getOriginalFilename());
        Invoice detachInvoice;
        try {
            invoiceDto.setUser(userToSimple.convert(userService.findByUserName(principal.getName())));
            detachInvoice = dtoToInvoice.convert(invoiceDto);
            //        invoice.setEntityStatus(Status.ACTIVE);
            invoiceService.save(detachInvoice);
        } catch (DateTimeParseException e) {
            // TODO
            model.addAttribute("deviceTitles", deviceTitleService.getAll());
            model.addAttribute("deviceCreationError", "Неверный формат даты");
            return INVOICE_CREATE_OR_UPDATE_FORM;
        }
        return "redirect:/invoices";
    }

    @GetMapping(value = "/files/{id}")
    public String redirectToGetFile(@PathVariable Long id) {
        String result;
        int index = invoiceService.findById(id).getPath().lastIndexOf('.');
        String extension = invoiceService.findById(id).getPath().substring(index + 1);
        switch (extension) {
            case "pdf":
                result = "redirect:/invoices/pdf/" + id;
                break;
            case "png":
                result = "redirect:/invoices/png/" + id;
                break;
            case "jpg":
                result = "redirect:/invoices/jpg/" + id;
                break;
            case "jpeg":
                result = "redirect:/invoices/jpg/" + id;
                break;
            default:
                result = "redirect:/invoices/download/" + id;
        }
        return result;
    }

    @GetMapping(value = "pdf/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody
    byte[] getPDFById(@PathVariable Long id) {
        try {
            return Files.readAllBytes(storageService.load("invoices", invoiceService.findById(id).getPath()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @GetMapping(value = "png/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody
    byte[] getPNGById(@PathVariable Long id) {
        try {
            return Files.readAllBytes(storageService.load("invoices", invoiceService.findById(id).getPath()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @GetMapping(value = "jpg/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getJPGById(@PathVariable Long id) {
        try {
            return Files.readAllBytes(storageService.load("invoices", invoiceService.findById(id).getPath()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @GetMapping(value = "download/{id}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable Long id) {

        Resource file = storageService.loadAsResource("invoices", invoiceService.findById(id).getPath());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

}
