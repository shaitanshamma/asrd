package com.kropotov.asrd.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ControlSystemDto {
    private Long id;

    private String number;

    private String purpose;

    private String purposePassport;

    private LocalDate vintage;

    private int vpNumber;

    private LocalDate otkDate;

    private LocalDate vpDate;
}
