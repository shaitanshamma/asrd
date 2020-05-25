package com.kropotov.asrd.entities;

import com.kropotov.asrd.entities.common.TitleEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "companies")
@Getter
@Setter
public class CompanyOld extends TitleEntity {
}
