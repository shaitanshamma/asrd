package com.kropotov.asrd.converters.items;

import com.kropotov.asrd.dto.items.DeviceComponentDto;
import com.kropotov.asrd.entities.items.DeviceComponent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DeviceComponentToDto implements Converter<DeviceComponent, DeviceComponentDto> {

    @Override
    public DeviceComponentDto convert(DeviceComponent deviceComponent) {
//        .location(source.getLocation())
        return null;
    }
}
