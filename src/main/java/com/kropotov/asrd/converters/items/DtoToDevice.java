package com.kropotov.asrd.converters.items;

import com.kropotov.asrd.converters.SimpleToUser;
import com.kropotov.asrd.converters.simples.items.SimpleToControlSystem;
import com.kropotov.asrd.converters.simples.items.SimpleToDeviceComponent;
import com.kropotov.asrd.dto.items.DeviceDto;
import com.kropotov.asrd.dto.items.SimpleDeviceComponent;
import com.kropotov.asrd.entities.items.Device;
import com.kropotov.asrd.entities.items.DeviceComponent;
import com.kropotov.asrd.services.springdatajpa.items.DeviceComponentService;
import com.kropotov.asrd.services.springdatajpa.items.DeviceService;
import com.kropotov.asrd.services.springdatajpa.titles.DeviceTitleService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DtoToDevice implements Converter<DeviceDto, Device> {

    private final DeviceTitleService deviceTitleService;
    private final DeviceService deviceService;
    private final SimpleToUser simpleToUser;
    private final SimpleToControlSystem simpleToControlSystem;
    private final SimpleToDeviceComponent simpleToDeviceComponent;
    private final DeviceComponentService deviceComponentService;

    @Synchronized
    @Nullable
    @Override
    public Device convert(@NonNull DeviceDto source) {
        if (source == null) {
            return null;
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        final Device device = Device.builder()
                .id(source.getId())
                .title(deviceTitleService.getById(source.getDeviceTitle().getId()).orElseThrow(
                        () -> new RuntimeException("Ошибка при выборе названия системы")
                ))
                .number(source.getNumber())
                .location(source.getLocation())
                .purpose(source.getPurpose())
                .purposePassport(source.getPurposePassport())
                .vintage((source.getVintage() == null || source.getVintage().equals("")) ? null : LocalDate.parse(source.getVintage(), dateFormatter))
                .otkDate((source.getOtkDate() == null || source.getOtkDate().equals("")) ? null : LocalDate.parse(source.getOtkDate(), dateFormatter))
                .vpDate((source.getVpDate() == null || source.getVpDate().equals("")) ? null : LocalDate.parse(source.getVpDate(), dateFormatter))
                .vpNumber(source.getVpNumber())
                .user(simpleToUser.convert(source.getUser()))
            .build();

        if (source.getSystem().getNumber() != null && !source.getSystem().getNumber().equals("")) {
            source.getSystem().setUser(source.getUser());
            device.setSystem(simpleToControlSystem.convert(source.getSystem()));
        } else if (source.getSystem().getId() != null) {
            device.setSystem(null);
        }

        if (source.getComponents() != null && source.getComponents().size() > 0) {
            for (SimpleDeviceComponent component : source.getComponents()) {
                if (component.getNumber() != null && !component.getNumber().equals("")) {
                    component.setUser(source.getUser());
                    DeviceComponent tmpDeviceComponent = simpleToDeviceComponent.convert(component);
                    tmpDeviceComponent.setDevice(device); //todo как это сделать автоматически средствами jpa
                    device.addComponent(tmpDeviceComponent);
                } else if (component.getId() != null) {
                    Optional<DeviceComponent> tmpDeviceComponent = deviceComponentService.getById(component.getId());
                    if(tmpDeviceComponent.isPresent()) {
                        tmpDeviceComponent.get().setDevice(null);
                        deviceComponentService.save(tmpDeviceComponent.get());
                    }
                }
            }
        }

        return device;
    }
}
