package com.poly.goldenbamboo.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="Tables")
public class TableEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int number;

	private boolean status;

	//bi-directional many-to-one association to OrderEntity
	@OneToMany(mappedBy="table")
	@JsonIgnore
	private List<OrderEntity> orders;

	//bi-directional many-to-one association to ReservationEntity
	@OneToMany(mappedBy="table")
	@JsonIgnore
	private List<ReservationEntity> reservations;

	//bi-directional many-to-one association to BranchEntity
	@ManyToOne
	@JsonIgnore
	private BranchEntity branch;


}