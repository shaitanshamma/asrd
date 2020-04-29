package com.kropotov.asrd.converters;

import com.kropotov.asrd.dto.ActInputControlDto;
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

    @Synchronized
    @Nullable
    @Override
    public ActInputControl convert(ActInputControlDto source) {
        if (source == null) {
            return null;
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        Invoice invoice = invoiceService.findById(source.getInvoiceId());

        final ActInputControl act = ActInputControl.builder()
                .id(source.getId())
                .path(source.getPath())
                .number(source.getNumber())
                .invoice(invoice)
                .date(LocalDate.parse(source.getDate(), dateFormatter))
                .result(source.getResult())
                .description(source.getDescription())
            .build();

        if (invoice.getSystems() != null && invoice.getSystems().size() > 0) {
            act.setSystems(invoice.getSystems());
        }

        if (invoice.getDevices() != null && invoice.getDevices().size() > 0) {
            act.setDevices(invoice.getDevices());
        }

        return act;
    }
}
