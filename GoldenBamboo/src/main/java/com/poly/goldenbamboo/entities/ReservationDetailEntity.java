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
@Table(name="ReservationDetails")
public class ReservationDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="dish_or_combo_id")
	private int dishOrComboId;

	private int quantity;

	private String type;

	//bi-directional many-to-one association to ReservationEntity
	@ManyToOne
	@JsonIgnore
	private ReservationEntity reservation;


}