package com.poly.goldenbamboo.mappers;

import org.springframework.stereotype.Component;
import com.poly.goldenbamboo.dtos.FoodDTO;
import com.poly.goldenbamboo.entities.CategoryEntity;
import com.poly.goldenbamboo.entities.DishEntity;
import com.poly.goldenbamboo.entities.MenuDishEntity;

@Component
public class FoodMapper {

	public FoodDTO toDTO(DishEntity e) {
		FoodDTO dto = new FoodDTO();
		dto.setId(e.getId());
		dto.setDescription(e.getDescription());
		dto.setImage(e.getImage());
		dto.setName(e.getName());
		dto.setPrice(e.getPrice());
		dto.setStatus(e.isStatus());
		dto.setCategory(e.getCategory());
		dto.setMenuDishes(e.getMenuDishes());
		
		return dto;
	}
	
	public DishEntity toEntity(FoodDTO d) {
		DishEntity entity = new DishEntity();
		entity.setId(d.getId());
		entity.setDescription(d.getDescription());
		entity.setImage(d.getImage());
		entity.setName(d.getName());
		entity.setPrice(d.getPrice());
		entity.setStatus(d.isStatus());
		
		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setId(d.getId());
		entity.setCategory(categoryEntity);
		
		MenuDishEntity menuDishEntity = new MenuDishEntity();
		menuDishEntity.setId(d.getId());
		entity.setMenuDishes(d.getMenuDishes());
		
		return entity;
	}
}
