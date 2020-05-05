package com.kropotov.asrd.entities.items;

import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.entities.common.InfoEntity;
import com.kropotov.asrd.entities.enums.Status;
import com.kropotov.asrd.entities.titles.SystemComponentTitle;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "system_components")
@AllArgsConstructor
@NoArgsConstructor
public class SystemComponent extends InfoEntity {

    @Builder
    public SystemComponent(Long id, Status entityStatus, LocalDateTime createdAt, LocalDateTime updatedAt,
                           SystemComponentTitle title, String number, User user) {

        super(id, entityStatus, createdAt, updatedAt);
        this.title = title;
        this.number = number;
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "title_system_component_id")
    private SystemComponentTitle title;

    private String number;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
