package com.kropotov.asrd.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "device_components_titles")
public class DeviceComponentTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

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

        return title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
