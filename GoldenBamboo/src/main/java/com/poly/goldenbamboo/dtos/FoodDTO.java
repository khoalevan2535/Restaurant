package com.poly.goldenbamboo.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.poly.goldenbamboo.entities.CategoryEntity;
import com.poly.goldenbamboo.entities.MenuDishEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodDTO {
	private int id;
	private String description;
	private String image;
	private String name;
	private BigDecimal price;
	private boolean status;
	private CategoryEntity category;
	private List<MenuDishEntity> menuDishes = new ArrayList<>();
}
