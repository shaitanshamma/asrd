package com.kropotov.asrd.dto.items;

import com.kropotov.asrd.dto.SimpleUser;
import com.kropotov.asrd.entities.titles.SystemTitle;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleControlSystem {
    private Long id;
    private SystemTitle systemTitle;
    private String number;
    private SimpleUser user;
}
