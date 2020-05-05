package com.kropotov.asrd.converters;

import com.kropotov.asrd.dto.SimpleUser;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimpleToUser implements Converter<SimpleUser, User> {

    private final UserService userService;

    @Synchronized
    @Nullable
    @Override
    public User convert(SimpleUser source) {
        if (source == null) {
            return null;
        }
        return userService.getById(source.getId()).orElseThrow(() -> new RuntimeException("Нет такого пользователя"));
    }
}
