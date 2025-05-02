package com.poly.goldenbamboo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.goldenbamboo.entities.DiscountEntity;


public interface DiscountJPA extends JpaRepository<DiscountEntity, Integer> {
    List<DiscountEntity> findByStatus(boolean status);
}
