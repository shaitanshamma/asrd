package com.kropotov.asrd.dto.items;

import com.kropotov.asrd.dto.SimpleUser;
import com.kropotov.asrd.entities.titles.SystemComponentTitle;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleSystemComponent {
    private Long id;
    private SystemComponentTitle systemComponentTitle;
    private String number;
    private SimpleUser user;
}
