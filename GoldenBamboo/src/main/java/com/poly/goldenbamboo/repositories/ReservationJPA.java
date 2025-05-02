package com.poly.goldenbamboo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.goldenbamboo.entities.ReservationEntity;

public interface ReservationJPA extends JpaRepository<ReservationEntity, Integer>{

}
