package com.poly.goldenbamboo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.goldenbamboo.entities.DishEntity;

public interface DishJPA extends JpaRepository<DishEntity, Integer>{


@Query("SELECT d FROM DishEntity d WHERE d.name = ?1")
    DishEntity findByName(String name);

@Query("SELECT d FROM DishEntity d JOIN d.discountDishes dd")
List<DishEntity> findDiscountedDishes();

}
