package com.kropotov.asrd.dto.items;

import com.kropotov.asrd.dto.SimpleUser;
import com.kropotov.asrd.entities.titles.SystemTitle;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ControlSystemDto {
    private Long id;

    private SystemTitle systemTitle;

    private String number;

    private String purpose;

    private String purposePassport;

    private String vintage;

    private int vpNumber;

    private String otkDate;

    private String vpDate;

    private List<SimpleDevice> devices = new ArrayList<>();

    private SimpleUser user;

    public void addDevice(SimpleDevice simpleDevice) {
        if (devices == null) {
            devices = new ArrayList<>();
        }
        devices.add(simpleDevice);
    }
}
