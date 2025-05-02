package com.poly.goldenbamboo.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="Branches")
public class BranchEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String address;

	private String description;

	private String name;

	@Column(name = "parent_id")
    private Integer parentId;

	private boolean status;

	//bi-directional many-to-one association to AccountEntity
	@OneToMany(mappedBy="branch", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<AccountEntity> accounts;

	//bi-directional many-to-one association to MenuEntity
	@OneToMany(mappedBy="branch", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<MenuEntity> menus;

	//bi-directional many-to-one association to OrderEntity
	@OneToMany(mappedBy="branch", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<OrderEntity> orders;

	//bi-directional many-to-one association to TableEntity
	@OneToMany(mappedBy="branch", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<TableEntity> tables;


}