package com.poly.goldenbamboo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.goldenbamboo.entities.CategoryEntity;
import com.poly.goldenbamboo.entities.DishEntity;
import com.poly.goldenbamboo.repositories.CategoryJPA;

@Service
public class CategoryService {
	@Autowired
	private CategoryJPA categoryJPA;
	
	public List<CategoryEntity> getAllCategory(){
		return categoryJPA.findAll();
	}
}
