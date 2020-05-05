package com.kropotov.asrd.converters.simples.items;

import com.kropotov.asrd.dto.items.SimpleDevice;
import com.kropotov.asrd.entities.items.Device;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceToSimple implements Converter<Device, SimpleDevice> {

    @Synchronized
    @Nullable
    @Override
    public SimpleDevice convert(@NonNull Device source) {
        if (source == null) {
            return null;
        }

        final SimpleDevice simpleDevice = SimpleDevice.builder()
                .id(source.getId())
                .deviceTitle(source.getTitle())
                .number(source.getNumber())
            .build();

        return simpleDevice;
    }
}
