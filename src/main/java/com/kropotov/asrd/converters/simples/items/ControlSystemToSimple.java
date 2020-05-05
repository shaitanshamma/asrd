package com.kropotov.asrd.converters.simples.items;

import com.kropotov.asrd.dto.items.SimpleControlSystem;
import com.kropotov.asrd.entities.items.ControlSystem;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ControlSystemToSimple implements Converter<ControlSystem, SimpleControlSystem> {

    @Synchronized
    @Nullable
    @Override
    public SimpleControlSystem convert(@NonNull ControlSystem source) {
        if (source == null) {
            return null;
        }

        final SimpleControlSystem simpleControlSystem = SimpleControlSystem.builder()
                .id(source.getId())
                .systemTitle(source.getTitle())
                .number(source.getNumber())
                .location(source.getLocation())
                .build();

        return simpleControlSystem;
    }
}
