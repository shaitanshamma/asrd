package com.kropotov.asrd.entities.common;

import com.kropotov.asrd.entities.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Value;

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
        this.entityStatus = Status.ACTIVE;
    }

    @Enumerated(value = EnumType.ORDINAL)
    @Value("1")
    private Status entityStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
