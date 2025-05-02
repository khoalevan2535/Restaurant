package com.poly.goldenbamboo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.goldenbamboo.entities.DishEntity;
import com.poly.goldenbamboo.entities.Menu_DishEntity;
import com.poly.goldenbamboo.services.DishService;

@RestController
@RequestMapping("/Dish")
public class DishController {
	@Autowired
	private DishService dishService;

	@GetMapping
	public List<DishEntity> getAllDish() {
		return dishService.getAllDish();
	}

	@GetMapping("/{id}")
	public DishEntity getDishById(@PathVariable("id") Integer id) {
		return dishService.getDishById(id);
	}
}