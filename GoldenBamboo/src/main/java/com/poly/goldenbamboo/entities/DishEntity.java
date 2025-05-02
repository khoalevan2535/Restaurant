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
@Table(name = "Dishes")
public class DishEntity implements Serializable {
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
	@OneToMany(mappedBy = "dishe")
	@JsonIgnore
	private List<Combo_DishEntity> comboDishes;

	// bi-directional many-to-one association to DiscountDishEntity
	@OneToMany(mappedBy = "dishe")
	@JsonIgnore
	private List<DiscountDishEntity> discountDishes;

	// bi-directional many-to-one association to CategoryEntity
	@ManyToOne
	@JsonIgnore
	private CategoryEntity category;

	// bi-directional many-to-one association to Menu_DishEntity
	@OneToMany(mappedBy = "dishe")
	@JsonIgnore
	private List<Menu_DishEntity> menuDishes;

	@OneToMany(mappedBy = "dish")
	@JsonIgnore
	private List<OrderDetailEntity> orderDetails;

}