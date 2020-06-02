package com.kropotov.asrd.entities.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "email")
    private String email;

    @Column(name = "position")
    private String position;

    @Column(name = "work_phone")
    private String workPhone;

    @Column(name = "mobil_phone")
    private String mobilPhone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="company_id")
    private Company company;

    @OneToMany(
            mappedBy = "employee",
            cascade = CascadeType.ALL)
    private List<EmployeePhone> employeePhones;
}
