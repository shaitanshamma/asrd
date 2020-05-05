package com.kropotov.asrd.converters.simples.items;

import com.kropotov.asrd.converters.SimpleToUser;
import com.kropotov.asrd.dto.items.SimpleControlSystem;
import com.kropotov.asrd.entities.items.ControlSystem;
import com.kropotov.asrd.entities.titles.SystemTitle;
import com.kropotov.asrd.services.springdatajpa.items.SystemService;
import com.kropotov.asrd.services.springdatajpa.titles.SystemTitleService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimpleToControlSystem  implements Converter<SimpleControlSystem, ControlSystem> {

    private final SystemTitleService systemTitleService;
    private final SystemService systemService;
    private final SimpleToUser simpleToUser;

    @Synchronized
    @Nullable
    @Override
    public ControlSystem convert(@NonNull SimpleControlSystem source) {
        if (source == null) {
            return null;
        }

        SystemTitle systemTitle = systemTitleService.getById(source.getSystemTitle().getId()).orElseThrow(
                () -> new RuntimeException("Ошибка при выборе названия системы")
        );

        ControlSystem controlSystemFromDB = systemService.getByNumberAndTitle(source.getNumber(), systemTitle);

        if (controlSystemFromDB != null) {
            return controlSystemFromDB;
        }

        final ControlSystem controlSystem = ControlSystem.builder()
                .title(systemTitle)
                .number(source.getNumber())
                .user(simpleToUser.convert(source.getUser()))
                .build();

        return controlSystem;
    }
}
