package com.poly.goldenbamboo.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="Menu_Combos")
public class Menu_ComboEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	//bi-directional many-to-one association to ComboEntity
	@ManyToOne
	@JsonIgnore
	private ComboEntity combo;

	//bi-directional many-to-one association to MenuEntity
	@ManyToOne
	@JoinColumn(name="menu_id")
	@JsonIgnore
	private MenuEntity menus;

}