package com.kropotov.asrd.entities.items;

import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.entities.common.InfoEntity;
import com.kropotov.asrd.entities.enums.Status;
import com.kropotov.asrd.entities.titles.DeviceComponentTitle;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "device_components")
@AllArgsConstructor
@NoArgsConstructor
public class DeviceComponent extends InfoEntity {

    @Builder
    public DeviceComponent(Long id, Status entityStatus, LocalDateTime createdAt, LocalDateTime updatedAt,
                           DeviceComponentTitle title, String number, Device device, User user) {

        super(id, entityStatus, createdAt, updatedAt);
        this.title = title;
        this.number = number;
        this.device = device;
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "device_component_title_id")
    private DeviceComponentTitle title;

    private String number;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public DeviceComponent(DeviceComponentTitle title, String number) {
        this.title = title;
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceComponent that = (DeviceComponent) o;

        return title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
