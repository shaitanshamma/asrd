package com.kropotov.asrd.dto.docs;

import com.kropotov.asrd.dto.company.CompanyDto;
import com.kropotov.asrd.dto.SimpleUser;
import com.kropotov.asrd.dto.items.SimpleControlSystem;
import com.kropotov.asrd.dto.items.SimpleDevice;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
    private Long id;
    private String path;

    private MultipartFile file;

    private String number;

    private String date;

    private CompanyDto from;

    private CompanyDto destination;

    private String description;

    private List<SimpleControlSystem> systems = new ArrayList<>();

    private List<SimpleDevice> devices = new ArrayList<>();
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

    // Заменить на HashMap? Заменить на итератор?
    public void removeSystemById(Long id) {
        for (SimpleControlSystem simpleControlSystem : systems) {
            if (id.equals(simpleControlSystem.getId())) {
                systems.remove(simpleControlSystem);
                return;
            }
        }
    }

    // Заменить на HashMap? Заменить на итератор?
    public void removeDeviceById(Long id) {
        for (SimpleDevice dimpleDevice : devices) {
            if (id.equals(dimpleDevice.getId())) {
                systems.remove(dimpleDevice);
                return;
            }
        }
    }
}
