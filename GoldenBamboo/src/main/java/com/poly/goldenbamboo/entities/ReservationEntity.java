package com.poly.goldenbamboo.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="Reservations")
public class ReservationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="set_date_and_time")
	private Timestamp setDateAndTime;

	//bi-directional many-to-one association to ReservationDetailEntity
	@OneToMany(mappedBy="reservation")
	@JsonIgnore
	private List<ReservationDetailEntity> reservationDetails;

	//bi-directional many-to-one association to AccountEntity
	@ManyToOne
	@JsonIgnore
	private AccountEntity account;

	//bi-directional many-to-one association to TableEntity
	@ManyToOne
	@JsonIgnore
	private TableEntity table;

	

}