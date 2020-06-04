package com.kropotov.asrd.dto.company;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyPhoneDto {
    private Long id;
    private String phone;
    private String email;
    private String description;
}
