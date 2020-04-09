package com.kropotov.asrd.entities.utils;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@MappedSuperclass
public abstract class TitleEntity extends BaseEntity {

    @NotNull(message = "is required")
    @Size(min = 2, message = "Title length must be greater then 2 symbols") // TODO символы не равны пустоте
    private String title;
}
