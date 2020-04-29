package com.kropotov.asrd.dto;

import com.kropotov.asrd.entities.items.DeviceComponent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DeviceDto {
    private Long id;
    private Long deviceTitleId;
    private String number;
    private String purpose;
    private String purposePassport;
    private LocalDate vintage;
    private int vpNumber;
    private LocalDate otkDate;
    private LocalDate vpDate;
    private Long systemId;
    private List<DeviceComponent> components = new ArrayList<>();
    private List<Long> invoicesId;
    private List<Long> actsId;
}
