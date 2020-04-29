package com.kropotov.asrd.dto;

import com.kropotov.asrd.entities.enums.Result;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ActInputControlDto {
    private Long id;
    private String path;
    private String number;
    private Long invoiceId;
    private String date;
    private Result result;
    private String description = "";
    private List<ControlSystemDto> systems = new ArrayList<>();
    private List<DeviceDto> devices  = new ArrayList<>();
}
