package com.kropotov.asrd.converters.items;

import com.kropotov.asrd.converters.SimpleToUser;
import com.kropotov.asrd.dto.items.DeviceComponentDto;
import com.kropotov.asrd.entities.items.DeviceComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DtoToDeviceComponent implements Converter<DeviceComponentDto, DeviceComponent> {

    private final SimpleToUser simpleToUser;

    @Override
    public DeviceComponent convert(DeviceComponentDto deviceComponentDto) {
        return null;
//        .user(simpleToUser.convert(source.getUser()))
    }
}
