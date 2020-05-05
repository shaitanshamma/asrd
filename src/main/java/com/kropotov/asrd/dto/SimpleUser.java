package com.kropotov.asrd.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUser {

    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
}
