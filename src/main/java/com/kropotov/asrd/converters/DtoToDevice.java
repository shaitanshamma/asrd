package com.kropotov.asrd.converters;

import com.kropotov.asrd.dto.DeviceDto;
import com.kropotov.asrd.entities.items.Device;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DtoToDevice implements Converter<DeviceDto, Device> {
    @Override
    public Device convert(DeviceDto deviceDto) {
        return null;
    }
}
