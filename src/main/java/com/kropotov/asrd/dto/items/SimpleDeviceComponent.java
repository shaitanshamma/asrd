package com.kropotov.asrd.dto.items;

import com.kropotov.asrd.dto.SimpleUser;
import com.kropotov.asrd.entities.titles.DeviceComponentTitle;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleDeviceComponent {
    private Long id;
    private DeviceComponentTitle deviceComponentTitle;
    private String number;
    private SimpleUser user;
}
