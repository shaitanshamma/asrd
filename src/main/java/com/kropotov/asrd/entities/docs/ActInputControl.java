package com.kropotov.asrd.entities.docs;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.entities.common.DocEntity;
import com.kropotov.asrd.entities.enums.Result;
import com.kropotov.asrd.entities.enums.Status;
import com.kropotov.asrd.entities.items.ControlSystem;
import com.kropotov.asrd.entities.items.Device;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "acts_input_control")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActInputControl extends DocEntity {

    @Builder
    public ActInputControl(Long id, Status entityStatus, LocalDateTime createdAt, LocalDateTime updatedAt, String path,
                           String number, Invoice invoice, LocalDate date, Result result, String description, User user,
                           List<ControlSystem> systems, List<Device> devices) {
        super(id, entityStatus, createdAt, updatedAt, path);
        this.number = number;
        this.invoice = invoice;
        this.date = date;
        this.result = result;
        this.description = description;
        this.user = user;
        this.systems = systems;
        this.devices = devices;
    }

    @Column(name = "number")
    private String number;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @Column (name = "act_date")
    private LocalDate date; // TODO

    @Enumerated(value = EnumType.ORDINAL)
    private Result result;

    @Column(name = "description")
    private String description = "";

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "actsInputControl", fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JsonBackReference
    private List<ControlSystem> systems;

    @ManyToMany(mappedBy = "actsInputControl", fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JsonBackReference
    private List<Device> devices;
}
