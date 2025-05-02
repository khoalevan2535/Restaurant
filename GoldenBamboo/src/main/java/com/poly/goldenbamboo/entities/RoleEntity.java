package com.poly.goldenbamboo.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="Roles")
public class RoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;

	private boolean status;

	//bi-directional many-to-one association to AccountEntity
	@OneToMany(mappedBy="role", fetch = FetchType.LAZY)
	@JsonIgnore
	@ToString.Exclude 
	private List<AccountEntity> accounts;


}