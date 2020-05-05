package com.kropotov.asrd.entities.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class TitleEntity extends BaseEntity {

    public TitleEntity(Long id,
                       @NotNull(message = "is required") @Size(min = 2, message = "Title length must be greater then 2 symbols") String title) {
        super(id);
        this.title = title;
    }

    @NotNull(message = "is required")
    @Size(min = 2, message = "Title length must be greater then 2 symbols") // TODO символы не равны пустоте
    private String title;

}
