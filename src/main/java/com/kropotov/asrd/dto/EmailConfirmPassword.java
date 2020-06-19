package com.kropotov.asrd.dto;

import com.kropotov.asrd.validation.ValidEmail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailConfirmPassword {
    @ValidEmail
    private String emailConfirm;

    private Integer passwordConfirm;
}
