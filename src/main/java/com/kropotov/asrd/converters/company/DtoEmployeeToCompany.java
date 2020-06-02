package com.kropotov.asrd.converters.company;

import com.kropotov.asrd.dto.company.EmployeeDto;
import com.kropotov.asrd.entities.company.Employee;
import com.kropotov.asrd.services.springdatajpa.titles.company.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DtoEmployeeToCompany implements Converter<EmployeeDto, Employee> {

    private final EmployeeService employeeService;

    @Nullable
    @Override
    public Employee convert(@NonNull EmployeeDto employeeDto) {
        if (employeeDto == null) {
            return null;
        }
        Optional<Employee> employee = employeeService.getById(employeeDto.getId());
        employee.get().setName(employeeDto.getName());
        employee.get().setLastName(employeeDto.getLastName());
        employee.get().setPatronymic(employeeDto.getPatronymic());
        employee.get().setWorkPhone(employeeDto.getWorkPhone());
        employee.get().setMobilPhone(employeeDto.getMobilPhone());
        employee.get().setPosition(employeeDto.getPosition());
        employee.get().setEmail(employeeDto.getEmail());

        return employee.get();
    }
}
