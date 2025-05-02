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
	private int dishOrComboId;

	private BigDecimal price;

	private int quantity;

	@Column(name = "type")
	private boolean type;

	// bi-directional many-to-one association to OrderEntity
	@ManyToOne
	@JsonIgnore
	private OrderEntity order;

	@ManyToOne
	@JoinColumn(name = "dish_id", referencedColumnName = "id")
	private DishEntity dish;

	// Cột tham chiếu đến Combos
	@ManyToOne
	@JoinColumn(name = "combo_id", referencedColumnName = "id")
	private ComboEntity combo;

	// Phương thức setDish sẽ gán type = true
	public void setDish(DishEntity dish) {
		this.dish = dish;
		this.type = true; // Cập nhật type khi là Dish
	}

	// Phương thức setCombo sẽ gán type = false
	public void setCombo(ComboEntity combo) {
		this.combo = combo;
		this.type = false; // Cập nhật type khi là Combo
	}

}