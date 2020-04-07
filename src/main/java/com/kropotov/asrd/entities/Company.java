package com.kropotov.asrd.entities;

import com.kropotov.asrd.entities.utils.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
public class Company extends BaseEntity {

    @Column(name = "title")
    @NotNull(message = "is required")
    @Size(min = 4, message = "Title length must be greater then 4 symbols") // TODO символы не равны пустоте
    private String title;
}
