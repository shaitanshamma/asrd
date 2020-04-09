package com.kropotov.asrd.entities.docs;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.entities.items.ControlSystem;
import com.kropotov.asrd.entities.items.Device;
import com.kropotov.asrd.entities.utils.DocEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "acts_input_control")
@Getter
@Setter
@NoArgsConstructor
public class ActInputControl extends DocEntity {

    @Column(name = "number")
    private String number;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @Column (name = "act_date")
    private LocalDate date; // TODO

    @Column(name = "result")
    private String result;

    @Column(name = "description")
    private String description;

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
