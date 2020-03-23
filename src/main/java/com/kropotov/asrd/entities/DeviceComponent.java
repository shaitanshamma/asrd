package com.kropotov.asrd.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "device_components")
@NoArgsConstructor
public class DeviceComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "device_component_title_id")
    private DeviceComponentTitle title;

    private String number;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    @Column(name = "entity_status")
    private String entityStatus;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

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
