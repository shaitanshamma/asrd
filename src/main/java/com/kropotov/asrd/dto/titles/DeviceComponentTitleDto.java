package com.kropotov.asrd.dto.titles;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeviceComponentTitleDto {
    private Long id;
    private String title;
    private Long deviceTitleId;
}
