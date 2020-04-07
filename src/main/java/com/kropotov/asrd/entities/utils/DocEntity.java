package com.kropotov.asrd.entities.utils;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class DocEntity extends InfoEntity {

    private String path;

    // TODO добавить дату документа
}
