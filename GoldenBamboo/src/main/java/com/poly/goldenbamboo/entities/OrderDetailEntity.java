package com.poly.goldenbamboo.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "OrderDetails")
public class OrderDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "discount_percentage")
	private BigDecimal discountPercentage;

	@Column(name = "dish_or_combo_id")
	private String dishOrComboId;

	private BigDecimal price;

	private int quantity;

	@Column(name = "type")
	private boolean type;

	// bi-directional many-to-one association to OrderEntity
	@ManyToOne
	@JsonIgnore
	private OrderEntity order;


}