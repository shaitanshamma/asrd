package com.kropotov.asrd.entities.items;

import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.entities.common.ItemEntity;
import com.kropotov.asrd.entities.enums.Location;
import com.kropotov.asrd.entities.enums.Status;
import com.kropotov.asrd.entities.titles.SystemComponentTitle;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "system_components")
@AllArgsConstructor
@NoArgsConstructor
public class SystemComponent extends ItemEntity {

    @Builder
    public SystemComponent(Long id, Status entityStatus, LocalDateTime createdAt, LocalDateTime updatedAt,
                           @NotNull(message = "Number cannot be null") String number, Location location, String purpose,
                           String purposePassport, LocalDate vintage, int vpNumber, LocalDate otkDate, LocalDate vpDate,
                           SystemComponentTitle title, User user) {

        super(id, entityStatus, createdAt, updatedAt, number, location, purpose, purposePassport, vintage, vpNumber, otkDate, vpDate);
        this.title = title;
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "title_system_component_id")
    private SystemComponentTitle title;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
