package com.kropotov.asrd.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDto {
    private Long id;
    private String title;
    private String email;
    private String fax;
    private String militaryRepresentation;
}
