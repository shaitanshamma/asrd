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
@Table(name = "companies")
public class Company {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "email")
    private String email;
    @Column(name = "fax")
    private String fax;
    @Column(name = "military_representation")
    private String militaryRepresentation;

    @OneToMany(
            mappedBy = "company",
            cascade = CascadeType.ALL)
    private List<CompanyPhone> companyPhones;

    @OneToMany(
            mappedBy = "company",
            cascade = CascadeType.ALL)
    private List<Address> address;

    @OneToMany(
            mappedBy = "company",
            cascade = CascadeType.ALL)
    private List<Employee> employee;
}
