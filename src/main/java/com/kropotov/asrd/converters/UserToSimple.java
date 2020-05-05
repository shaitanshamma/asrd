package com.kropotov.asrd.converters;

import com.kropotov.asrd.dto.SimpleUser;
import com.kropotov.asrd.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserToSimple implements Converter<User, SimpleUser> {


    @Synchronized
    @Nullable
    @Override
    public SimpleUser convert(User source) {
        if (source == null) {
            return null;
        }

        final SimpleUser simpleUser = SimpleUser.builder()
                .id(source.getId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .patronymic(source.getPatronymic())
            .build();

        return simpleUser;
    }
}
