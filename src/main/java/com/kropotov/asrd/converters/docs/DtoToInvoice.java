package com.kropotov.asrd.converters.docs;

import com.kropotov.asrd.converters.DtoToCompany;
import com.kropotov.asrd.converters.SimpleToUser;
import com.kropotov.asrd.converters.simples.items.SimpleToControlSystem;
import com.kropotov.asrd.converters.simples.items.SimpleToDevice;
import com.kropotov.asrd.dto.docs.InvoiceDto;
import com.kropotov.asrd.entities.docs.Invoice;
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
                .path(source.getPath())
                .number(source.getNumber())
                .date((source.getDate() == null || source.getDate().equals("")) ? null : LocalDate.parse(source.getDate(), dateFormatter))
                .from(companyConverter.convert(source.getFrom()))
                .destination(companyConverter.convert(source.getDestination()))
                .description(source.getDescription())
                .user(simpleToUser.convert(source.getUser()))
            .build();

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
