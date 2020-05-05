package com.kropotov.asrd.converters.titles;

import com.kropotov.asrd.dto.titles.DeviceTitleDto;
import com.kropotov.asrd.entities.titles.DeviceTitle;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceTitleToDto implements Converter<DeviceTitle, DeviceTitleDto> {
    @Override
    public DeviceTitleDto convert(DeviceTitle deviceTitle) {
        return null;
    }
}
