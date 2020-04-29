package com.kropotov.asrd.converters;

import com.kropotov.asrd.dto.ActInputControlDto;
import com.kropotov.asrd.entities.docs.ActInputControl;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class ActInputControlToDto implements Converter<ActInputControl, ActInputControlDto> {
    private final InvoiceToDto invoiceConverter;
    private final DeviceToDto deviceConverter;
    private final ControlSystemToDto systemConverter;

    @Synchronized
    @Nullable
    @Override
    public ActInputControlDto convert(ActInputControl source) {
        if (source == null) {
            return null;
        }

        final ActInputControlDto actDto = ActInputControlDto.builder()
                .id(source.getId())
                .path(source.getPath())
                .number(source.getNumber())
                .invoiceId(source.getId())
                .result(source.getResult())
                .description(source.getDescription())
                .build();
        if (source.getDate() != null) {
            actDto.setDate(source.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        }

        if (source.getSystems() != null && source.getSystems().size() > 0) {
            source.getSystems()
                    .forEach(system -> actDto.getSystems().add(systemConverter.convert(system)));
        }

        if (source.getDevices() != null && source.getDevices().size() > 0) {
            source.getDevices()
                    .forEach(device -> actDto.getDevices().add(deviceConverter.convert(device)));
        }

        return actDto;
    }
}
