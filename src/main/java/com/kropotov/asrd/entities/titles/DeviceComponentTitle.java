package com.kropotov.asrd.entities.titles;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kropotov.asrd.entities.common.TitleEntity;
import com.kropotov.asrd.entities.items.DeviceComponent;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "device_components_titles")
public class DeviceComponentTitle extends TitleEntity {

    @ManyToOne
    @JoinColumn(name = "device_title_id")
    @JsonBackReference
    private DeviceTitle deviceTitle;

    @OneToMany(mappedBy = "title")
    @JsonBackReference
    private List<DeviceComponent> deviceComponents;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceComponentTitle that = (DeviceComponentTitle) o;

        return this.getTitle().equals(that.getTitle());
    }

    @Override
    public int hashCode() {
        return this.getTitle().hashCode();
    }
}
