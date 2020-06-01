package com.kropotov.asrd.entities;

import com.kropotov.asrd.entities.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@Table(name = "roles")
public class Role extends BaseEntity {

	@Column(name = "name")
	private String name;

	public Role(String name) {
		this.name = name;
	}

}
