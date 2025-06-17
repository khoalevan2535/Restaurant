package com.poly.goldenbamboo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.goldenbamboo.dtos.FoodDTO;
import com.poly.goldenbamboo.entities.DishEntity;
import com.poly.goldenbamboo.mappers.FoodMapper;
import com.poly.goldenbamboo.repositories.DishJPA;

@Service
public class DishService {
	@Autowired
	private DishJPA dishJPA;
	
	@Autowired
	private FoodMapper foodMapper;

	public List<DishEntity> getAllDish() {
		return dishJPA.findAll();
	}

	public DishEntity getDishById(Integer id) {
		Optional<DishEntity> optional = dishJPA.findById(id);
		return optional.get();
	}

//	public List<DishEntity> getDishesFromDefaultMenu(Integer branchId) {
//        return dishJPA.findDefaultMenuDishesByBranchAndCategory(branchId);
//    }
//	
	public List<DishEntity> getDefaultMenuDishesByBranchAndCategory(Integer branchId, Integer categoryId) {
	//	return dishJPA.findDefaultMenuDishesByBranchAndCategory(branchId, categoryId);
		return null;
	}
	
	public List<DishEntity> getDishesByIds(List<Integer> ids) {
	    return dishJPA.findAllById(ids);
	}
	
	//Tạo món ăn
	public FoodDTO createDish(FoodDTO dto) {
	    DishEntity entity = foodMapper.toEntity(dto);
	    DishEntity savedDishEntity = dishJPA.save(entity);
	    FoodDTO resultFoodDTO = foodMapper.toDTO(savedDishEntity);
	    return resultFoodDTO;
	}

}
