package com.kropotov.asrd.dto.company;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {

    private Long id;

    private String zipCode;

    private String city;

    private String street;

    private String place;

    private String description;
}
