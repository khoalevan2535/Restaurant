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
@Table(name="DiscountDishes")
public class DiscountDishEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	//bi-directional many-to-one association to DiscountEntity
	@ManyToOne
	private DiscountEntity discount;

	//bi-directional many-to-one association to DishEntity
	@ManyToOne
	@JoinColumn(name="dish_id")
	@JsonIgnore
	private DishEntity dishe;

	

}