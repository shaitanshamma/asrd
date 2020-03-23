package com.kropotov.asrd.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "systems")
public class ControlSystem {

    public enum Status {
        ACTIVE("active"), DISABLE("disable");

        String entity_status;

        Status (String entity_status) {this.entity_status = entity_status;}
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // генерируем данное значение как primary key auto-increment из БД
    @Column(name = "id")
    private Long id;

    // @NotNull(message = "Title cannot be null")
    @ManyToOne
    @JoinColumn(name = "title_system_id")
    private SystemTitle title;

    @NotNull(message = "Number cannot be null")
    @Column(name = "number")
    private String number;

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

    @NotNull(message = "User cannot be null")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToMany
    @JoinTable(
            name = "acts_ic_systems",
            joinColumns = @JoinColumn(name = "system_id"),
            inverseJoinColumns = @JoinColumn(name = "act_ic_id")
    )
    private List<ActInputControl> actsInputControl;

    @ManyToMany
    @JoinTable(
            name = "systems_invoices",
            joinColumns = @JoinColumn(name = "system_id"),
            inverseJoinColumns = @JoinColumn(name = "invoice_id")
    )
    @JsonBackReference
    private List<Invoice> invoices;
    
    @OneToMany(mappedBy = "system")
    private List<Device> devices;

    @Override
    public String toString() {
        return "ControlSystem{" +
                "id=" + id +
                ", title=" + title.getTitle() +
                ", number='" + number + '\'' +
                ", purpose='" + purpose + '\'' +
                ", purposePassport='" + purposePassport + '\'' +
                ", vintage=" + vintage +
                ", vp=" + vpNumber +
                ", acceptOTK=" + otkDate +
                ", acceptVP=" + vpDate +
                ", entityStatus='" + entityStatus + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", user=" + user.getUserName() +
                '}';
    }
}
