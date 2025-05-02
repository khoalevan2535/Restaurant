package com.poly.goldenbamboo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.goldenbamboo.entities.CategoryEntity;
import com.poly.goldenbamboo.entities.DishEntity;

public interface CategoryJPA extends JpaRepository<CategoryEntity, Integer>{
	
}
