package com.kropotov.asrd.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "device_title_id")
    private DeviceTitle title;

    private String number;

    @ManyToOne
    @JoinColumn(name = "system_id")
    private ControlSystem system;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "purpose_passport")
    private String purposePassport;

    @Column(name = "vintage")
    private LocalDate vintage;

    @Column(name = "vp_number")
    private int vpNumber;

    @Column(name = "accept_otk_date")
    private LocalDate otkDate;

    @Column(name = "accept_vp_date")
    private LocalDate vpDate;

    @Column(name = "entity_status")
    private String entityStatus;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

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
