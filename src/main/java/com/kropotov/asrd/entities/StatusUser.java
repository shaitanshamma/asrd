package com.kropotov.asrd.entities;

import com.kropotov.asrd.entities.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "status_user")
public class StatusUser extends BaseEntity {

    @Column(name = "name")
    private String name;

    public StatusUser(String name) {
        this.name = name;
    }
}
