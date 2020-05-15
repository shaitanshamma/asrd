package com.kropotov.asrd.entities.common;

import com.kropotov.asrd.entities.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Audited(withModifiedFlag = true)
public abstract class InfoEntity extends BaseEntity {

    public InfoEntity(Long id, Status entityStatus) {
        super(id);
        if (entityStatus == null) {
            this.entityStatus = Status.ACTIVE;
        } else {
            this.entityStatus = entityStatus;
        }
    }

    @Enumerated(value = EnumType.ORDINAL)
    private Status entityStatus;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
