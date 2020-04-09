package com.kropotov.asrd.entities.utils;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@Getter
@Setter
@MappedSuperclass
public abstract class InfoEntity extends BaseEntity {

    @Enumerated(value = EnumType.ORDINAL)
    private Status entityStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
