package com.poly.goldenbamboo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.poly.goldenbamboo.entities.MenuDishEntity;
import com.poly.goldenbamboo.repositories.MenuDishJPA;

@Service
public class MenuDishService {

	@Autowired
	private MenuDishJPA menuDishJPA;

	public List<MenuDishEntity> getAllMenuDish(){
		return menuDishJPA.findAll();
	}

	public Optional<MenuDishEntity> getById(Integer id) {
	    return menuDishJPA.findById(id);
	}
}
