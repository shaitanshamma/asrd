package com.kropotov.asrd.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "title")
    @NotNull(message = "is required")
    @Size(min = 4, message = "Title length must be greater then 4 symbols") // TODO символы не равны пустоте
    private String title;
}
