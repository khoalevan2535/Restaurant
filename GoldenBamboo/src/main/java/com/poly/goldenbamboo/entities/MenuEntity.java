package com.poly.goldenbamboo.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="Menus")
public class MenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String name;
	
	@Column(name = "is_default")
    private Boolean isDefault;
	//bi-directional many-to-one association to Menu_ComboEntity
	@OneToMany(mappedBy="menus")
	@JsonIgnore
	private List<Menu_ComboEntity> menuCombos;

	//bi-directional many-to-one association to Menu_DishEntity
	@OneToMany(mappedBy="menus")
	@JsonIgnore
	private List<Menu_DishEntity> menuDishes;

	//bi-directional many-to-one association to BranchEntity
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "branch_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BranchEntity branch;
	



}