package com.kropotov.asrd.converters.items;

import com.kropotov.asrd.converters.UserToSimple;
import com.kropotov.asrd.converters.simples.items.DeviceToSimple;
import com.kropotov.asrd.dto.items.ControlSystemDto;
import com.kropotov.asrd.dto.items.SimpleDevice;
import com.kropotov.asrd.entities.items.ControlSystem;
import com.kropotov.asrd.entities.titles.DeviceTitle;
import com.kropotov.asrd.services.springdatajpa.titles.SystemTitleService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ControlSystemToDto implements Converter<ControlSystem, ControlSystemDto> {

    private final SystemTitleService systemTitleService;
    private final DeviceToSimple deviceToSimple;
    private final UserToSimple userToSimple;

    @Synchronized
    @Nullable
    @Override
    public ControlSystemDto convert(@NonNull ControlSystem source) {
        if (source == null) {
            return null;
        }

        final ControlSystemDto controlSystemDto = ControlSystemDto.builder()
                .id(source.getId())
                .systemTitle(source.getTitle())
                .number(source.getNumber())
                .purpose(source.getPurpose())
                .purposePassport(source.getPurposePassport())
                .vintage(source.getVintage() != null ? source.getVintage().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) : null)
                .otkDate(source.getOtkDate() != null ? source.getOtkDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) : null)
                .vpDate(source.getVpDate() != null ? source.getVpDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) : null)
                .vpNumber(source.getVpNumber())
                .user(userToSimple.convert(source.getUser()))
            .build();

        List<DeviceTitle> deviceTitles = source.getTitle().getDeviceTitles();

        if (source.getDevices() != null && source.getDevices().size() > 0) {

            List<SimpleDevice> simpleDevices = new ArrayList<>();
            source.getDevices()
                    .forEach(device -> simpleDevices.add(deviceToSimple.convert(device)));

            if (deviceTitles.size() != source.getDevices().size() || !deviceTitles.equals(source.getDevices())) {
                deviceTitles
                        .forEach(deviceTitle -> controlSystemDto.addDevice(SimpleDevice.builder().deviceTitle(deviceTitle).build()));


                for (int i = 0; i < controlSystemDto.getDevices().size(); i++) {
                    Iterator<SimpleDevice> deviceIterator = simpleDevices.iterator();
                    while (deviceIterator.hasNext()) {
                        SimpleDevice simpleDevice = deviceIterator.next();
                        if (controlSystemDto.getDevices().get(i).getDeviceTitle().getId().equals(simpleDevice.getDeviceTitle().getId())
                                && !simpleDevice.getNumber().equals(controlSystemDto.getDevices().get(i).getNumber())) {
                            if (controlSystemDto.getDevices().get(i).getNumber() == null || controlSystemDto.getDevices().get(i).getNumber().equals("")) {
                                controlSystemDto.getDevices().set(i, simpleDevice);
                            } else {
                                controlSystemDto.getDevices().add(i, simpleDevice);
                            }
                            deviceIterator.remove();
                        }
                    }
                }
            } else {
                controlSystemDto.setDevices(simpleDevices);
            }
        } else {
            deviceTitles
                    .forEach(deviceTitle -> controlSystemDto.addDevice(SimpleDevice.builder().deviceTitle(deviceTitle).build()));
        }
        return controlSystemDto;
    }
}
