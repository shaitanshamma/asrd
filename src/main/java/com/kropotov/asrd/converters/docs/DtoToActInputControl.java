package com.kropotov.asrd.converters.docs;

import com.kropotov.asrd.converters.SimpleToUser;
import com.kropotov.asrd.dto.docs.ActInputControlDto;
import com.kropotov.asrd.entities.docs.ActInputControl;
import com.kropotov.asrd.entities.docs.Invoice;
import com.kropotov.asrd.services.ActInputControlService;
import com.kropotov.asrd.services.StorageService;
import com.kropotov.asrd.services.springdatajpa.docs.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class DtoToActInputControl implements Converter<ActInputControlDto, ActInputControl> {

    private final InvoiceService invoiceService;
    private final SimpleToUser simpleToUser;
    private final StorageService storageService;
    private final ActInputControlService actInputControlService;

    @Synchronized
    @Nullable
    @Override
    public ActInputControl convert(ActInputControlDto source) {
        if (source == null) {
            return null;
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        Invoice invoice = invoiceService.getById(source.getInvoice().getId()).orElseThrow(
                () -> new RuntimeException("Накладная не выбрана")
        );

        final ActInputControl act = ActInputControl.builder()
                .id(source.getId())
                .path(source.getPath())
                .number(source.getNumber())
                .invoice(invoice)
                .date((source.getDate() == null || source.getDate().equals("")) ? null : LocalDate.parse(source.getDate(), dateFormatter))
                .result(source.getResult())
                .description(source.getDescription())
                .user(simpleToUser.convert(source.getUser()))
                .build();

        if (invoice.getSystems() != null && invoice.getSystems().size() > 0) {
            invoice.getSystems().forEach(act::addSystem);

        }

        if (invoice.getDevices() != null && invoice.getDevices().size() > 0) {
            invoice.getDevices().forEach(act::addDevice);
        }

//todo убрать так много copy-paste из методов конвертации

        if (source.getFile() != null && !("").equals(source.getFile().getOriginalFilename())) {
            String extension = source.getFile().getOriginalFilename().substring(source.getFile().getOriginalFilename().lastIndexOf('.'));
            String filename = "act_" + act.getNumber() + "_" + act.getDate() + extension;
            act.setPath(storageService.store("acts", filename, source.getFile()));
        } else if (source.getId() != null) {
            actInputControlService.getById(source.getId()).ifPresent(actFromDB -> act.setPath(actFromDB.getPath()));
        }

        return act;
    }
}
