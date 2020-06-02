package com.kropotov.asrd.converters.docs;

import com.kropotov.asrd.converters.company.DtoToCompany;
import com.kropotov.asrd.converters.SimpleToUser;
import com.kropotov.asrd.converters.simples.items.SimpleToControlSystem;
import com.kropotov.asrd.converters.simples.items.SimpleToDevice;
import com.kropotov.asrd.dto.docs.InvoiceDto;
import com.kropotov.asrd.entities.docs.Invoice;
import com.kropotov.asrd.services.StorageService;
import com.kropotov.asrd.services.springdatajpa.docs.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class DtoToInvoice implements Converter<InvoiceDto, Invoice> {

    private final DtoToCompany companyConverter;
    private final SimpleToDevice deviceConverter;
    private final SimpleToControlSystem systemConverter;
    private final SimpleToUser simpleToUser;
    private final StorageService storageService;
    private final InvoiceService invoiceService;

    @Synchronized
    @Nullable
    @Override
    public Invoice convert(@NonNull InvoiceDto source) {
        if (source == null) {
            return null;
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        final Invoice invoice = Invoice.builder()
                .id(source.getId())
                .number(source.getNumber())
                .date((source.getDate() == null || source.getDate().equals("")) ? null : LocalDate.parse(source.getDate(), dateFormatter))
                .from(companyConverter.convert(source.getFrom()))
                .destination(companyConverter.convert(source.getDestination()))
                .description(source.getDescription())
                .user(simpleToUser.convert(source.getUser()))
                .build();

        if (source.getFile() != null && !("").equals(source.getFile().getOriginalFilename())) {
            //TODO сделать так, чтобы пути к файлам не были жестко зашиты в коде. Надо ли? Или сделать таблицу с индексацией имен?
            String extension = source.getFile().getOriginalFilename().substring(source.getFile().getOriginalFilename().lastIndexOf('.'));
            String filename = "invoice_" + invoice.getNumber() + "_" + invoice.getDate() + extension;
            invoice.setPath(storageService.store("invoices", filename, source.getFile()));
        } else if (source.getId() != null) {
            //todo сделать как-то по умному без лишних обращений к БД
            invoiceService.getById(source.getId()).ifPresent(invoiceFromDB -> invoice.setPath(invoiceFromDB.getPath()));
            //invoice.setPath(invoiceService.getById(source.getId()).orElse(null).getPath());
        }

        if (source.getSystems() != null && source.getSystems().size() > 0) {
            source.getSystems().forEach(system -> system.setUser(source.getUser())); // todo объединить в один цикл
            source.getSystems()
                    .forEach(system -> invoice.addSystem(systemConverter.convert(system)));
        }

        if (source.getDevices() != null && source.getDevices().size() > 0) {
            source.getDevices().forEach(device -> device.setUser(source.getUser())); // todo объединить в один цикл
            source.getDevices()
                    .forEach(device -> invoice.addDevice(deviceConverter.convert(device)));
        }

        return invoice;
    }
}
