package com.kropotov.asrd.converters.docs;

import com.kropotov.asrd.converters.company.CompanyToDto;
import com.kropotov.asrd.converters.simples.items.ControlSystemToSimple;
import com.kropotov.asrd.converters.simples.items.DeviceToSimple;
import com.kropotov.asrd.dto.docs.InvoiceDto;
import com.kropotov.asrd.entities.docs.Invoice;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class InvoiceToDto implements Converter<Invoice, InvoiceDto> {

    private final CompanyToDto companyConverter;
    private final DeviceToSimple deviceConverter;
    private final ControlSystemToSimple systemConverter;

    @Synchronized
    @Nullable
    @Override
    public InvoiceDto convert(@NonNull Invoice source) {
        if (source == null) {
            return null;
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        final InvoiceDto invoiceDto = InvoiceDto.builder()
                .id(source.getId())
                .path(source.getPath())
                .number(source.getNumber())
                .from(companyConverter.convert(source.getFrom()))
                .destination(companyConverter.convert(source.getDestination()))
                .description(source.getDescription())
                .build();

        if (source.getDate() != null) {
            invoiceDto.setDate(source.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        }

        if (source.getSystems() != null && source.getSystems().size() > 0) {
            source.getSystems()
                    .forEach(system -> invoiceDto.addSystem(systemConverter.convert(system)));
        }

        if (source.getDevices() != null && source.getDevices().size() > 0) {
            source.getDevices()
                    .forEach(device -> invoiceDto.addDevice(deviceConverter.convert(device)));
        }

        return invoiceDto;
    }
}
