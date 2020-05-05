package com.kropotov.asrd.entities;

import com.kropotov.asrd.entities.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role extends BaseEntity {

	@Column(name = "name")
	private String name;

	public Role() {
	}

	public Role(String name) {
		this.name = name;
	}

}
