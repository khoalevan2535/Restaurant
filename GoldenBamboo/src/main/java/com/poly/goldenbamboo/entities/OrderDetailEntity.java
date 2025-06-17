package com.poly.goldenbamboo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "order_details")
public class OrderDetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "discount_percentage", precision = 5, scale = 2)
	private BigDecimal discountPercentage;

	@Column(name = "dish_or_combo_id", nullable = false)
	private int dishOrComboId;
	
	@Column(name = "price", nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Column(name = "type", nullable = false)
	private boolean type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "order_id") 
	private OrderEntity order;


}