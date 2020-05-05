package com.kropotov.asrd.dto.items;

import com.kropotov.asrd.dto.SimpleUser;
import com.kropotov.asrd.entities.titles.DeviceTitle;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DeviceDto {
    private Long id;
    private DeviceTitle deviceTitle;
    private String number;
    private String purpose;
    private String purposePassport;
    private String vintage;
    private int vpNumber;
    private String otkDate;
    private String vpDate;
    private SimpleControlSystem system;
    private List<SimpleDeviceComponent> components = new ArrayList<>();
    private SimpleUser user;
//    private List<Long> invoicesId;
//    private List<Long> actsId;

    public void addComponent(SimpleDeviceComponent simpleDeviceComponent) {
        if (components == null) {
            components = new ArrayList<>();
        }
        components.add(simpleDeviceComponent);
    }
}
