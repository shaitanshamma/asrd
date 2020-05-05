package com.kropotov.asrd.entities;

import com.kropotov.asrd.entities.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity {

	@Column(name = "username")
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "patronymic")
	private String patronymic;

	@Column(name = "email")
	private String email;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "users_roles",
	joinColumns = @JoinColumn(name = "user_id"),
	inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Collection<Role> roles;

	public User() {
	}

	public User(String userName, String password, String firstName, String lastName, String patronymic, String email) {
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.patronymic = patronymic;
		this.email = email;
	}

	public User(String userName, String password, String firstName, String lastName, String patronymic, String email,
                Collection<Role> roles) {
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.patronymic = patronymic;
		this.email = email;
		this.roles = roles;
	}

}
