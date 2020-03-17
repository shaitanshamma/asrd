package com.kropotov.asrd.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    /*public void setInvoice(Invoice invoice)

    private void setInvoices(List<Invoice> invoices) {

    }*/
}
