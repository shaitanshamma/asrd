package com.kropotov.asrd.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "acts_input_control")
@Data
@NoArgsConstructor
public class ActInputControl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @Column (name = "act_date")
    private LocalDate date; // TODO

    @Column(name = "path")
    private String path;

    @Column(name = "result")
    private String result;

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
            name = "acts_ic_systems",
            joinColumns = @JoinColumn(name = "act_ic_id"),
            inverseJoinColumns = @JoinColumn(name = "system_id")
    )
    @JsonBackReference
    private List<ControlSystem> systems;
}
