package com.poly.goldenbamboo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.goldenbamboo.entities.DiscountComboEntity;

public interface DiscountComboJPA extends JpaRepository<DiscountComboEntity, Integer>{

    List<DiscountComboEntity> findByComboId(Integer comboId);
    List<DiscountComboEntity> findByDiscountId(Integer discountId);
	
}
