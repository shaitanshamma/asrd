package com.kropotov.asrd.converters;

import com.kropotov.asrd.dto.ControlSystemDto;
import com.kropotov.asrd.entities.items.ControlSystem;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ControlSystemToDto implements Converter<ControlSystem, ControlSystemDto> {
    @Override
    public ControlSystemDto convert(ControlSystem controlSystem) {
        return null;
    }
}
