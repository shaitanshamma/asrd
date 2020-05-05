package com.kropotov.asrd.converters.simples.items;

import com.kropotov.asrd.converters.SimpleToUser;
import com.kropotov.asrd.dto.items.SimpleDeviceComponent;
import com.kropotov.asrd.entities.items.DeviceComponent;
import com.kropotov.asrd.entities.titles.DeviceComponentTitle;
import com.kropotov.asrd.services.springdatajpa.items.DeviceComponentService;
import com.kropotov.asrd.services.springdatajpa.titles.DeviceComponentTitleService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimpleToDeviceComponent  implements Converter<SimpleDeviceComponent, DeviceComponent> {

    private final DeviceComponentTitleService deviceComponentTitleService;
    private final DeviceComponentService deviceComponentService;
    private final SimpleToUser simpleToUser;

    @Synchronized
    @Nullable
    @Override
    public DeviceComponent convert(@NonNull SimpleDeviceComponent source) {
        if (source == null) {
            return null;
        }

        DeviceComponentTitle deviceComponentTitle = deviceComponentTitleService
                .getById(source.getDeviceComponentTitle().getId()).orElseThrow(
                () -> new RuntimeException("Ошибка при выборе названия СЧ прибора")
        );


        DeviceComponent deviceComponentFromDB = deviceComponentService.getByNumberAndTitle(source.getNumber(), deviceComponentTitle);

        if (deviceComponentFromDB != null) {
            return deviceComponentFromDB;
        }

        final DeviceComponent deviceComponent = DeviceComponent.builder()
                .title(deviceComponentTitle)
                .number(source.getNumber())
                .user(simpleToUser.convert(source.getUser()))
                .build();

        return deviceComponent;
    }
}
