package com.kropotov.asrd.converters.simples.items;

import com.kropotov.asrd.converters.SimpleToUser;
import com.kropotov.asrd.dto.items.SimpleDevice;
import com.kropotov.asrd.entities.items.Device;
import com.kropotov.asrd.entities.titles.DeviceTitle;
import com.kropotov.asrd.services.springdatajpa.items.DeviceService;
import com.kropotov.asrd.services.springdatajpa.titles.DeviceTitleService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimpleToDevice implements Converter<SimpleDevice, Device> {

    private final DeviceTitleService deviceTitleService;
    private final DeviceService deviceService;
    private final SimpleToUser simpleToUser;

    @Synchronized
    @Nullable
    @Override
    public Device convert(@NonNull SimpleDevice source) {
        if (source == null) {
            return null;
        }

        DeviceTitle deviceTitle = deviceTitleService.getById(source.getDeviceTitle().getId()).orElseThrow(
                () -> new RuntimeException("Ошибка при выборе названия системы")
        );

        Device deviceFromDB = deviceService.getByNumberAndTitle(source.getNumber(), deviceTitle);

        if (deviceFromDB != null) {
            return deviceFromDB;
        }

        final Device device = Device.builder()
                .title(deviceTitle)
                .number(source.getNumber())
                .user(simpleToUser.convert(source.getUser()))
            .build();

        return device;
    }
}
