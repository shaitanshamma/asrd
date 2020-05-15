package com.kropotov.asrd.dto.titles;

import java.util.ArrayList;
import java.util.List;

import com.kropotov.asrd.dto.titles.SystemTitleDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TopicDto {
    private Long id;
    private String title;

    private List<SystemTitleDto> systemTitlesCommand = new ArrayList<>();
}
