package com.kropotov.asrd.dto.titles;

import java.util.ArrayList;
import java.util.List;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SystemTitleDto {
    private Long id;
    private String title;
    private List<TopicDto> topics = new ArrayList<>();
    private List<DeviceTitleDto> deviceTitles  = new ArrayList<>();
    private List<SystemComponentTitleDto> systemComponentTitles  = new ArrayList<>();

}
