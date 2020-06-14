package com.kropotov.asrd.converters.company;

import com.kropotov.asrd.dto.company.EmployeeDto;
import com.kropotov.asrd.entities.company.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
public class EmployeeToDto implements Converter<Employee, EmployeeDto> {

    @Nullable
    @Override
    public EmployeeDto convert(@NonNull Employee employee) {

        Assert.notNull(employee, "Объект employee не может быть пустым");

        return EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .lastName(employee.getLastName())
                .patronymic(employee.getPatronymic())
                .email(employee.getEmail())
                .position(employee.getPosition())
                .mobilPhone(employee.getMobilPhone())
                .workPhone(employee.getWorkPhone())
                .build();
    }
}
