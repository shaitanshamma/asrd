package com.kropotov.asrd.entities.utils;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class TitleEntity extends BaseEntity {

    private String title;
}
