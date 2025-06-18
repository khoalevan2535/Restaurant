package com.poly.goldenbamboo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.poly.goldenbamboo.entities.DiscountDishEntity;

public interface Discount_DishesJPA extends JpaRepository<DiscountDishEntity, Integer> {

}
