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
@Table(name="DiscountCombos")
public class DiscountComboEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="discount_percentage")
	private BigDecimal discountPercentage;

	//bi-directional many-to-one association to ComboEntity
	@ManyToOne
	@JsonIgnore
	private ComboEntity combo;

	//bi-directional many-to-one association to DiscountEntity
	@ManyToOne
	@JsonIgnore
	private DiscountEntity discount;



}