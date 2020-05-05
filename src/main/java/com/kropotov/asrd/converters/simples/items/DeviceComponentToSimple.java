package com.kropotov.asrd.converters.simples.items;

import com.kropotov.asrd.dto.items.SimpleDeviceComponent;
import com.kropotov.asrd.entities.items.DeviceComponent;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceComponentToSimple  implements Converter<DeviceComponent, SimpleDeviceComponent> {

    @Synchronized
    @Nullable
    @Override
    public SimpleDeviceComponent convert(@NonNull DeviceComponent source) {
        if (source == null) {
            return null;
        }

        final SimpleDeviceComponent simpleDeviceComponent = SimpleDeviceComponent.builder()
                .id(source.getId())
                .deviceComponentTitle(source.getTitle())
                .number(source.getNumber())
                .location(source.getLocation())
                .build();

        return simpleDeviceComponent;
    }
}
