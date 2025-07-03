package com.poly.goldenbamboo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.goldenbamboo.entities.OrderEntity;

public interface OrderJPA extends JpaRepository<OrderEntity, Integer>{
	Optional<OrderEntity> findByTableIdAndStatusNot(Integer tableId, Integer status);
	List<OrderEntity> findByTableIdAndStatusNotOrderByOrderDateDesc(Integer tableId, Integer status);
}
