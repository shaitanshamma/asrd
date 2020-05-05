package com.kropotov.asrd.converters.simples.items;

import com.kropotov.asrd.dto.items.SimpleSystemComponent;
import com.kropotov.asrd.entities.items.SystemComponent;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

public class SystemComponentToSimple implements Converter<SystemComponent, SimpleSystemComponent> {

    @Synchronized
    @Nullable
    @Override
    public SimpleSystemComponent convert(SystemComponent source) {
        if (source == null) {
            return null;
        }

        final SimpleSystemComponent simpleSystemComponent = SimpleSystemComponent.builder()
                .id(source.getId())
                .systemComponentTitle(source.getTitle())
                .number(source.getNumber())
                .build();

        return simpleSystemComponent;
    }
}
