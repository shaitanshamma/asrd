package com.kropotov.asrd.entities.items;

import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.entities.common.ItemEntity;
import com.kropotov.asrd.entities.docs.ActInputControl;
import com.kropotov.asrd.entities.docs.Invoice;
import com.kropotov.asrd.entities.enums.Location;
import com.kropotov.asrd.entities.enums.Status;
import com.kropotov.asrd.entities.titles.SystemTitle;
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
@Table(name = "systems")
@AllArgsConstructor
@NoArgsConstructor
public class ControlSystem extends ItemEntity {

    @Builder
    public ControlSystem(Long id, Status entityStatus, LocalDateTime createdAt, LocalDateTime updatedAt,
                         @NotNull(message = "Number cannot be null") String number, Location location, String purpose, String purposePassport,
                         LocalDate vintage, int vpNumber, LocalDate otkDate, LocalDate vpDate, SystemTitle title,
                         @NotNull(message = "User cannot be null") User user, List<ActInputControl> actsInputControl,
                         List<Invoice> invoices, List<Device> devices) {

        super(id, entityStatus, createdAt, updatedAt, number, location, purpose, purposePassport, vintage, vpNumber, otkDate, vpDate);
        this.title = title;
        this.user = user;
        this.actsInputControl = actsInputControl;
        this.invoices = invoices;
        this.devices = devices;
    }

    // @NotNull(message = "Title cannot be null")
    @ManyToOne
    @JoinColumn(name = "title_system_id")
    private SystemTitle title;

    @NotNull(message = "User cannot be null")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToMany(mappedBy = "systems", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    private List<ActInputControl> actsInputControl;

    @ManyToMany(mappedBy = "systems", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "system", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    private List<Device> devices;

    public void addDevice(Device device) {
        if (devices == null) {
            devices = new ArrayList<>();
        }
        devices.add(device);
    }

}
