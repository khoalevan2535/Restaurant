package com.poly.goldenbamboo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.goldenbamboo.entities.ReservationEntity;
import com.poly.goldenbamboo.repositories.ReservationJPA;

@Service
public class ReservationService {
	@Autowired
	private ReservationJPA reservationJPA;

	public List<ReservationEntity> getAllReservation() {
		return reservationJPA.findAll();
	}
}
