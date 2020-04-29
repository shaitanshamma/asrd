package com.kropotov.asrd.converters;

import com.kropotov.asrd.dto.ControlSystemDto;
import com.kropotov.asrd.entities.items.ControlSystem;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DtoToControlSystem implements Converter<ControlSystemDto, ControlSystem> {
    @Override
    public ControlSystem convert(ControlSystemDto controlSystemDto) {
        return null;
    }
}
