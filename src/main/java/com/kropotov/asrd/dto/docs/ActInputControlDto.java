package com.kropotov.asrd.dto.docs;

import com.kropotov.asrd.dto.SimpleUser;
import com.kropotov.asrd.dto.items.SimpleControlSystem;
import com.kropotov.asrd.dto.items.SimpleDevice;
import com.kropotov.asrd.entities.enums.Result;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActInputControlDto {
    private Long id;
    private String path;
    private String number;
    private InvoiceDto invoice;
    private String date;
    private Result result;
    private String description = "";
    private List<SimpleControlSystem> systems = new ArrayList<>();
    private List<SimpleDevice> devices  = new ArrayList<>();
    private SimpleUser user;

    public void addSystem(SimpleControlSystem simpleControlSystem) {
        if (systems == null) {
            systems = new ArrayList<>();
        }
        systems.add(simpleControlSystem);

    }

    public void addDevice(SimpleDevice simpleDevice) {
        if (devices == null) {
            devices = new ArrayList<>();
        }
        devices.add(simpleDevice);

    }
}
