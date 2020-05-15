package com.kropotov.asrd.entities.docs;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kropotov.asrd.entities.Company;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.entities.common.DocEntity;
import com.kropotov.asrd.entities.enums.Status;
import com.kropotov.asrd.entities.items.ControlSystem;
import com.kropotov.asrd.entities.items.Device;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice extends DocEntity {

    @Builder
    public Invoice(Long id, Status entityStatus, LocalDateTime createdAt, LocalDateTime updatedAt, String path,
                   @NotNull(message = "is required") @Size(min = 1, message = "Number length must be greater then 4 symbols") String number,
                   LocalDate date, @NotNull Company from, @NotNull Company destination, String description, User user,
                   List<ControlSystem> systems, List<Device> devices) {

        super(id, entityStatus, path);
        this.number = number;
        this.date = date;
        this.from = from;
        this.destination = destination;
        this.description = description;
        this.user = user;
        this.systems = systems;
        this.devices = devices;
    }

    @Column(name = "number")
    @NotNull(message = "is required")
    @Size(min = 1, message = "Number length must be greater then 4 symbols")
    private String number;

    @Column (name = "invoice_date")
    //@NotNull(message = "is required")
    // TODO формат даты @Size(min = 1, message = "Date length must have format '2001-3-15'")
    private LocalDate date; // TODO

    @ManyToOne
    @JoinColumn(name = "from_company_id")
    @NotNull
    private Company from;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    @NotNull
    private Company destination;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(
            name = "systems_invoices",
            joinColumns = @JoinColumn(name = "invoice_id"),
            inverseJoinColumns = @JoinColumn(name = "system_id")
    )
    private List<ControlSystem> systems;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable (
            name = "invoice_id_device_id",
            joinColumns = @JoinColumn(name = "invoice_id"),
            inverseJoinColumns = @JoinColumn(name = "device_id")
    )
    @JsonBackReference
    private List<Device> devices;

    public void addSystem(ControlSystem controlSystem) {
        if (systems == null) {
            systems = new ArrayList<>();
        }
        systems.add(controlSystem);
    }

    public void addDevice(Device device) {
        if (devices == null) {
            devices = new ArrayList<>();
        }
        devices.add(device);
    }
}
