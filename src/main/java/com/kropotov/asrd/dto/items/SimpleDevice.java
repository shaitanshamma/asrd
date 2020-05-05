package com.kropotov.asrd.dto.items;

import com.kropotov.asrd.dto.SimpleUser;
import com.kropotov.asrd.entities.titles.DeviceTitle;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleDevice {
    private Long id;
    private DeviceTitle deviceTitle;
    private String number;
    private SimpleUser user;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleDevice that = (SimpleDevice) o;

        if (deviceTitle.getId() != null ? !deviceTitle.getId().equals(that.deviceTitle.getId()) : that.deviceTitle.getId() != null) return false;
        return number != null ? number.equals(that.number) : that.number == null;
    }

    @Override
    public int hashCode() {
        int result = deviceTitle.getId() != null ? deviceTitle.getId().hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }
}
