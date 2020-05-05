package com.kropotov.asrd.converters.items;

import com.kropotov.asrd.converters.UserToSimple;
import com.kropotov.asrd.converters.simples.items.ControlSystemToSimple;
import com.kropotov.asrd.converters.simples.items.DeviceComponentToSimple;
import com.kropotov.asrd.dto.items.DeviceDto;
import com.kropotov.asrd.dto.items.SimpleDeviceComponent;
import com.kropotov.asrd.entities.items.Device;
import com.kropotov.asrd.entities.titles.DeviceComponentTitle;
import com.kropotov.asrd.services.springdatajpa.titles.DeviceTitleService;
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
public class DeviceToDto implements Converter<Device, DeviceDto> {

    private final DeviceTitleService deviceTitleService;
    private final ControlSystemToSimple controlSystemToSimple;
    private final DeviceComponentToSimple deviceComponentToSimple;
    private final UserToSimple userToSimple;


    @Synchronized
    @Nullable
    @Override
    public DeviceDto convert(@NonNull Device source) {
        if (source == null) {
            return null;
        }

        final DeviceDto deviceDto = DeviceDto.builder()
                .id(source.getId())
                .deviceTitle(source.getTitle())
                .number(source.getNumber())
                .purpose(source.getPurpose())
                .purposePassport(source.getPurposePassport())
                .vintage(source.getVintage() != null ? source.getVintage().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) : null)
                .otkDate(source.getOtkDate() != null ? source.getOtkDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) : null)
                .vpDate(source.getVpDate() != null ? source.getVpDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) : null)
                .vpNumber(source.getVpNumber())
                .system(controlSystemToSimple.convert(source.getSystem()))
                .user(userToSimple.convert(source.getUser()))
            .build();

        List<DeviceComponentTitle> deviceComponentTitles = source.getTitle().getComponentTitles();

        if (source.getComponents() != null && source.getComponents().size() > 0) {

            List<SimpleDeviceComponent> simpleDeviceComponents = new ArrayList<>();
            source.getComponents()
                    .forEach(deviceComponent -> simpleDeviceComponents.add(deviceComponentToSimple.convert(deviceComponent)));

            if (deviceComponentTitles.size() != source.getComponents().size() || !deviceComponentTitles.equals(source.getComponents())) {
                deviceComponentTitles
                        .forEach(deviceComponentTitle -> deviceDto.addComponent(SimpleDeviceComponent.builder().deviceComponentTitle(deviceComponentTitle).build()));


                for (int i = 0; i < deviceDto.getComponents().size(); i++) {
                    Iterator<SimpleDeviceComponent> deviceComponentIterator = simpleDeviceComponents.iterator();
                    while (deviceComponentIterator.hasNext()) {
                        SimpleDeviceComponent simpleDeviceComponent = deviceComponentIterator.next();
                        if (deviceDto.getComponents().get(i).getDeviceComponentTitle().getId().equals(simpleDeviceComponent.getDeviceComponentTitle().getId())
                                && !simpleDeviceComponent.getNumber().equals(deviceDto.getComponents().get(i).getNumber())) {
                            if (deviceDto.getComponents().get(i).getNumber() == null || deviceDto.getComponents().get(i).getNumber().equals("")) {
                                deviceDto.getComponents().set(i, simpleDeviceComponent);
                            } else {
                                deviceDto.getComponents().add(i, simpleDeviceComponent);
                            }
                            deviceComponentIterator.remove();
                        }
                    }
                }
            } else {
                deviceDto.setComponents(simpleDeviceComponents);
            }
        } else {
            deviceComponentTitles
                    .forEach(deviceComponentTitle -> deviceDto.addComponent(SimpleDeviceComponent.builder().deviceComponentTitle(deviceComponentTitle).build()));
        }


        return deviceDto;
    }
}
