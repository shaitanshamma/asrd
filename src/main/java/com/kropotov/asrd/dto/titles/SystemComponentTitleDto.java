package com.kropotov.asrd.dto.titles;


import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SystemComponentTitleDto {
    private Long id;
    private String title;
    private List<SystemTitleDto> systemTitleDtos;
}
