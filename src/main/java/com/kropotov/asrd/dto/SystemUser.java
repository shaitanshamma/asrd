package com.kropotov.asrd.dto;


import com.kropotov.asrd.entities.Role;
import com.kropotov.asrd.entities.StatusUser;
import com.kropotov.asrd.entities.User;
import com.kropotov.asrd.validation.FieldMatch;
import com.kropotov.asrd.validation.ValidEmail;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
@NoArgsConstructor
@FieldMatch(first = "password", second = "matchingPassword", message = "Поля пароля должны совпадать")
public class SystemUser {

    private Long id;

    @NotNull(message = "not null check")
    @Size(min = 3, message = "username length must be greater than 2 symbols")
    private String userName;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String password;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String matchingPassword;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String firstName;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String lastName;

    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String patronymic;

    private Collection<Role> roles;

    @ValidEmail
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    private String email;

    private String workPhone;

    private String mobilePhone;

    private StatusUser statusUser;

    public SystemUser(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.patronymic = user.getPatronymic();
        this.roles = user.getRoles();
        this.email = user.getEmail();
        this.workPhone = user.getWorkPhone();
        this.mobilePhone = user.getMobilePhone();
        this.statusUser = user.getStatusUser();
    }
}
