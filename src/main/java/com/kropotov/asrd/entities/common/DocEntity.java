package com.kropotov.asrd.entities.common;

import com.kropotov.asrd.entities.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class DocEntity extends InfoEntity {

    public DocEntity(Long id, Status entityStatus, LocalDateTime createdAt, LocalDateTime updatedAt, String path) {
        super(id, entityStatus, createdAt, updatedAt);
        this.path = path;
    }

    private String path;

    // TODO добавить дату документа
}
