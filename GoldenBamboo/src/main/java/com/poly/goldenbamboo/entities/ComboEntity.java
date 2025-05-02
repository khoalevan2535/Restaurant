package com.poly.goldenbamboo.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "Combos")

public class ComboEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String description;

	private String image;

	private String name;

	private BigDecimal price;

	private boolean status;

	// bi-directional many-to-one association to Combo_DishEntity
	@OneToMany(mappedBy = "combo", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Combo_DishEntity> comboDishes;

	// bi-directional many-to-one association to DiscountComboEntity
	@OneToMany(mappedBy = "combo")
	@JsonIgnore
	private List<DiscountComboEntity> discountCombos;

	// bi-directional many-to-one association to Menu_ComboEntity
	@OneToMany(mappedBy = "combo")
	@JsonIgnore
	private List<Menu_ComboEntity> menuCombos;

	@OneToMany(mappedBy = "combo")
	@JsonIgnore
	private List<OrderDetailEntity> orderDetails;

}