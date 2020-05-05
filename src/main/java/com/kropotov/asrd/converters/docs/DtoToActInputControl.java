package com.kropotov.asrd.converters.docs;

import com.kropotov.asrd.converters.SimpleToUser;
import com.kropotov.asrd.dto.docs.ActInputControlDto;
import com.kropotov.asrd.entities.docs.ActInputControl;
import com.kropotov.asrd.entities.docs.Invoice;
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

    @Synchronized
    @Nullable
    @Override
    public ActInputControl convert(ActInputControlDto source) {
        if (source == null) {
            return null;
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        Invoice invoice = invoiceService.findById(source.getInvoice().getId());

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

        return act;
    }
}
