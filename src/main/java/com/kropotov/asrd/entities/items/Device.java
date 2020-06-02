package com.kropotov.asrd.entities.items;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.entities.common.IFiles;
import com.kropotov.asrd.entities.common.ItemEntity;
import com.kropotov.asrd.entities.common.PageableEntity;
import com.kropotov.asrd.entities.docs.ActInputControl;
import com.kropotov.asrd.entities.docs.File;
import com.kropotov.asrd.entities.docs.Invoice;
import com.kropotov.asrd.entities.enums.Location;
import com.kropotov.asrd.entities.enums.Status;
import com.kropotov.asrd.entities.titles.DeviceTitle;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "devices")
@AllArgsConstructor
@NoArgsConstructor
public class Device extends ItemEntity implements PageableEntity, IFiles {

    @Builder
    public Device(Long id, Status entityStatus, LocalDateTime createdAt, LocalDateTime updatedAt,
                  @NotNull(message = "Number cannot be null") String number, Location location, String purpose,
                  String purposePassport, LocalDate vintage, int vpNumber, LocalDate otkDate, LocalDate vpDate,
                  DeviceTitle title, ControlSystem system, List<DeviceComponent> components, List<Invoice> invoices,
                  List<ActInputControl> actsInputControl, User user) {

        super(id, entityStatus, createdAt, updatedAt, number, location, purpose, purposePassport, vintage, vpNumber, otkDate, vpDate);
        this.title = title;
        this.system = system;
        this.components = components;
        this.invoices = invoices;
        this.actsInputControl = actsInputControl;
        this.user = user;
    }


    @ManyToOne
    @JoinColumn(name = "device_title_id")
    private DeviceTitle title;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "system_id")
    private ControlSystem system;

    @OneToMany(mappedBy = "device", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    private List<DeviceComponent> components;

    @ManyToMany(mappedBy = "devices", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JsonBackReference
    private List<Invoice> invoices;

    @ManyToMany(mappedBy = "devices", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    private List<ActInputControl> actsInputControl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "device_files",
            joinColumns = @JoinColumn(name = "device_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    private List<File> files;

    public void addComponent(DeviceComponent deviceComponent) {
        if (components == null) {
            components = new ArrayList<>();
        }
        components.add(deviceComponent);
    }

    @Override
    public boolean addFile(File file) {
        return files.add(file);
    }

    @Override
    public boolean removeFile(File file) {
       return files.remove(file);
    }
}
