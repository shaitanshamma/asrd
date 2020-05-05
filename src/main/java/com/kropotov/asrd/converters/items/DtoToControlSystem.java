package com.kropotov.asrd.converters.items;

import com.kropotov.asrd.converters.SimpleToUser;
import com.kropotov.asrd.converters.simples.items.SimpleToDevice;
import com.kropotov.asrd.dto.items.ControlSystemDto;
import com.kropotov.asrd.dto.items.SimpleDevice;
import com.kropotov.asrd.entities.items.ControlSystem;
import com.kropotov.asrd.entities.items.Device;
import com.kropotov.asrd.services.springdatajpa.items.DeviceService;
import com.kropotov.asrd.services.springdatajpa.items.SystemService;
import com.kropotov.asrd.services.springdatajpa.titles.SystemTitleService;
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
public class DtoToControlSystem implements Converter<ControlSystemDto, ControlSystem> {

    private final SystemTitleService systemTitleService;
    private final SystemService systemService;
    private final SimpleToDevice simpleToDevice;
    private final SimpleToUser simpleToUser;
    private final DeviceService deviceService;

    @Synchronized
    @Nullable
    @Override
    public ControlSystem convert(@NonNull ControlSystemDto source) {
        if (source == null) {
            return null;
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        final ControlSystem controlSystem = ControlSystem.builder()
                .id(source.getId())
                .title(systemTitleService.getById(source.getSystemTitle().getId()).orElseThrow(
                        () -> new RuntimeException("Ошибка при выборе названия системы")
                ))
                .number(source.getNumber())
                .purpose(source.getPurpose())
                .purposePassport(source.getPurposePassport())
                .vintage((source.getVintage() == null || source.getVintage().equals("")) ? null : LocalDate.parse(source.getVintage(), dateFormatter))
                .otkDate((source.getOtkDate() == null || source.getOtkDate().equals("")) ? null : LocalDate.parse(source.getOtkDate(), dateFormatter))
                .vpDate((source.getVpDate() == null || source.getVpDate().equals("")) ? null : LocalDate.parse(source.getVpDate(), dateFormatter))
                .vpNumber(source.getVpNumber())
                .user(simpleToUser.convert(source.getUser()))
            .build();

        if (source.getDevices() != null && source.getDevices().size() > 0) {
            for (SimpleDevice d : source.getDevices()) {
                if (d.getNumber() != null && !d.getNumber().equals("")) {
                    d.setUser(source.getUser());
                    Device tmpDevice = simpleToDevice.convert(d);
                    tmpDevice.setSystem(controlSystem); //todo как это сделать автоматически средствами jpa
                    controlSystem.addDevice(tmpDevice);
                } else if (d.getId() != null) {
                    Optional<Device> tmpDevice = deviceService.getById(d.getId());
                    if(tmpDevice.isPresent()) {
                        tmpDevice.get().setSystem(null);
                        deviceService.save(tmpDevice.get());
                    }
                }
            }
        }

        return controlSystem;
    }
}
