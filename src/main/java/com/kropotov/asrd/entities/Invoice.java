package com.kropotov.asrd.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoices")
@Data
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    @NotNull(message = "is required")
    @Size(min = 1, message = "Number length must be greater then 4 symbols")
    private String number;

    @Column (name = "invoice_date")
    //@NotNull(message = "is required")
    // TODO формат даты @Size(min = 1, message = "Date length must have format '2001-3-15'")
    private LocalDate date; // TODO

    @Column(name = "path")
    private String path;

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

    @Column(name = "entity_status")
    private String entityStatus;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @CreationTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "systems_invoices",
            joinColumns = @JoinColumn(name = "invoice_id"),
            inverseJoinColumns = @JoinColumn(name = "system_id")
    )
    @JsonBackReference
    private List<ControlSystem> systems;

    @ManyToMany
    @JoinTable (
            name = "invoice_id_device_id",
            joinColumns = @JoinColumn(name = "invoice_id"),
            inverseJoinColumns = @JoinColumn(name = "device_id")
    )
    @JsonBackReference
    private List<Device> devices;

}
