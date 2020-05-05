package com.kropotov.asrd.entities.common;

import com.kropotov.asrd.entities.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
public abstract class InfoEntity extends BaseEntity {

    public InfoEntity(Long id, Status entityStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id);
        if (entityStatus == null) {
            this.entityStatus = Status.ACTIVE;
        } else {
            this.entityStatus = entityStatus;
        }
    }

    @Enumerated(value = EnumType.ORDINAL)
    private Status entityStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
