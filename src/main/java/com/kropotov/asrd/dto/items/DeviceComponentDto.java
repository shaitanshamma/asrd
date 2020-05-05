package com.kropotov.asrd.dto.items;

import com.kropotov.asrd.dto.SimpleUser;
import com.kropotov.asrd.entities.enums.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeviceComponentDto {
    private SimpleUser user;
    private Location location;
}
