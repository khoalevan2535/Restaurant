package com.poly.goldenbamboo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.goldenbamboo.entities.OrderEntity;

public interface OrderJPA extends JpaRepository<OrderEntity, Integer>{

}
