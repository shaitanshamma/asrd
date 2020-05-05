package com.kropotov.asrd.converters.simples.items;

import com.kropotov.asrd.converters.SimpleToUser;
import com.kropotov.asrd.dto.items.SimpleSystemComponent;
import com.kropotov.asrd.entities.items.SystemComponent;
import com.kropotov.asrd.entities.titles.SystemComponentTitle;
import com.kropotov.asrd.services.springdatajpa.items.SystemComponentService;
import com.kropotov.asrd.services.springdatajpa.titles.SystemComponentTitleService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimpleToSystemComponent  implements Converter<SimpleSystemComponent, SystemComponent> {

    private final SystemComponentTitleService systemComponentTitleService;
    private final SystemComponentService systemComponentService;
    private final SimpleToUser simpleToUser;

    @Synchronized
    @Nullable
    @Override
    public SystemComponent convert(@NonNull SimpleSystemComponent source) {
        if (source == null) {
            return null;
        }

        SystemComponentTitle systemComponentTitle = systemComponentTitleService
                .getById(source.getSystemComponentTitle().getId()).orElseThrow(
                () -> new RuntimeException("Ошибка при выборе названия СЧ системы")
        );

        SystemComponent systemComponentFromDB = systemComponentService.getByNumberAndTitle(source.getNumber(), systemComponentTitle);

        if (systemComponentFromDB != null) {
            return systemComponentFromDB;
        }

        final SystemComponent systemComponent = SystemComponent.builder()
                .title(systemComponentTitle)
                .number(source.getNumber())
                .user(simpleToUser.convert(source.getUser()))
            .build();

        return systemComponent;
    }
}
