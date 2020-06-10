package com.kropotov.asrd.dto.company;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {
    private Long id;
    private String name;
    private String lastName;
    private String patronymic;
    private String email;
    private String position;
    private String workPhone;
    private String mobilPhone;
}
