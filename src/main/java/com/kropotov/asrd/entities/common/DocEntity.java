package com.kropotov.asrd.entities.common;

import com.kropotov.asrd.entities.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class DocEntity extends InfoEntity {

    public DocEntity(Long id, Status entityStatus, String path) {
        super(id, entityStatus);
        this.path = path;
    }

    private String path;

    // TODO добавить дату документа
}
