package com.kropotov.asrd.entities.items;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.entities.docs.Invoice;
import com.kropotov.asrd.entities.titles.DeviceTitle;
import com.kropotov.asrd.entities.utils.ItemEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "devices")
public class Device extends ItemEntity {

    @ManyToOne
    @JoinColumn(name = "device_title_id")
    private DeviceTitle title;

    @ManyToOne
    @JoinColumn(name = "system_id")
    private ControlSystem system;

    @OneToMany(mappedBy = "device")
    List<DeviceComponent> components;

    @ManyToMany
    @JoinTable (
            name = "invoice_id_device_id",
            joinColumns = @JoinColumn(name = "device_id"),
            inverseJoinColumns = @JoinColumn(name = "invoice_id")
    )
    @JsonBackReference
    private List<Invoice> invoices;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
