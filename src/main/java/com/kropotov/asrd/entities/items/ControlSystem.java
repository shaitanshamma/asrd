package com.kropotov.asrd.entities.items;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.entities.docs.ActInputControl;
import com.kropotov.asrd.entities.docs.Invoice;
import com.kropotov.asrd.entities.titles.SystemTitle;
import com.kropotov.asrd.entities.utils.ItemEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "systems")
public class ControlSystem extends ItemEntity {

    // @NotNull(message = "Title cannot be null")
    @ManyToOne
    @JoinColumn(name = "title_system_id")
    private SystemTitle title;

    @NotNull(message = "User cannot be null")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(
            name = "acts_ic_systems",
            joinColumns = @JoinColumn(name = "system_id"),
            inverseJoinColumns = @JoinColumn(name = "act_ic_id")
    )
    private List<ActInputControl> actsInputControl;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(
            name = "systems_invoices",
            joinColumns = @JoinColumn(name = "system_id"),
            inverseJoinColumns = @JoinColumn(name = "invoice_id")
    )
    @JsonBackReference
    private List<Invoice> invoices;
    
    @OneToMany(mappedBy = "system")
    private List<Device> devices;

}
