package com.poly.goldenbamboo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.goldenbamboo.entities.Menu_DishEntity;
import com.poly.goldenbamboo.services.DishService;
import com.poly.goldenbamboo.services.MenuDishService;

@RestController
@RequestMapping("/MenuDish")
public class MenuDishController {
	@Autowired
	private MenuDishService menuDishService;

	
	@GetMapping
	public List<Menu_DishEntity> getMenuDish(){
		return menuDishService.getAllMenuDish();
	}
	


}
