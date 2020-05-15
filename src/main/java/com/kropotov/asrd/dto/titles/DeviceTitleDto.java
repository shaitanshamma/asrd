package com.kropotov.asrd.dto.titles;

import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceTitleDto {

    private Long id;
    private String title;
    private List<SystemTitleDto> systemTitleDtos;
    private List<DeviceComponentTitleDto> componentTitles;
}
