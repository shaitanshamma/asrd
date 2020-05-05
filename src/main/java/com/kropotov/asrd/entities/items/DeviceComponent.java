package com.kropotov.asrd.entities.items;

import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.entities.common.ItemEntity;
import com.kropotov.asrd.entities.enums.Location;
import com.kropotov.asrd.entities.enums.Status;
import com.kropotov.asrd.entities.titles.DeviceComponentTitle;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "device_components")
@AllArgsConstructor
@NoArgsConstructor
public class DeviceComponent extends ItemEntity {

    @Builder
    public DeviceComponent(Long id, Status entityStatus, LocalDateTime createdAt, LocalDateTime updatedAt,
                           @NotNull(message = "Number cannot be null") String number, Location location, String purpose,
                           String purposePassport, LocalDate vintage, int vpNumber, LocalDate otkDate, LocalDate vpDate,
                           DeviceComponentTitle title, Device device, User user) {

        super(id, entityStatus, createdAt, updatedAt, number, location, purpose, purposePassport, vintage, vpNumber, otkDate, vpDate);
        this.title = title;
        this.device = device;
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "device_component_title_id")
    private DeviceComponentTitle title;


    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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
