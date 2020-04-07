package com.kropotov.asrd.entities.utils;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
public class ItemEntity extends InfoEntity {
    @NotNull(message = "Number cannot be null")
    @Column(name = "number")
    private String number;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "purpose_passport")
    private String purposePassport;

    @Column(name = "vintage")
    private LocalDate vintage;

    @Column(name = "vp_number")
    private int vpNumber;

    @Column(name = "accept_otk_date")
    private LocalDate otkDate;

    @Column(name = "accept_vp_date")
    private LocalDate vpDate;
}
