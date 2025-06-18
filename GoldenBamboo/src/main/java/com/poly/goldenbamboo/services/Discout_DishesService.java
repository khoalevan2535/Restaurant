package com.poly.goldenbamboo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.goldenbamboo.dtos.Discount_DishesDTO;
import com.poly.goldenbamboo.entities.DiscountDishEntity;
import com.poly.goldenbamboo.exception.ResourceNoFoundException;
import com.poly.goldenbamboo.mappers.Discount_DishesMapper;
import com.poly.goldenbamboo.repositories.Discount_DishesJPA;


@Service
public class Discout_DishesService {
    @Autowired
    Discount_DishesJPA discount_DishesJPA;

    public void deleteById(int id) {
        discount_DishesJPA.deleteById(id);
    }

    public Discount_DishesDTO creaateDiscount_Dishes(Discount_DishesDTO discount_DishesDTO) {
        DiscountDishEntity discount_DishEntity = Discount_DishesMapper.toEntity(discount_DishesDTO);
        discount_DishEntity = discount_DishesJPA.save(discount_DishEntity);
        return Discount_DishesMapper.toDTO(discount_DishEntity);
    }

    public List<Discount_DishesDTO> getAllDiscount_Dishes() {
        List<DiscountDishEntity> discount_DishEntities = discount_DishesJPA.findAll();
        return discount_DishEntities.stream()
                .map(Discount_DishesMapper::toDTO)
                .toList();
    }

    public void deleteDiscount_Dishes(int id) {
        DiscountDishEntity discount_DishEntity = discount_DishesJPA.findById(id)
                .orElseThrow(() -> new ResourceNoFoundException("Discount_Dishes not found with id: " + id));
        discount_DishesJPA.delete(discount_DishEntity);
    }

    public Discount_DishesDTO getDiscount_DishesById(int id) {
        DiscountDishEntity discount_DishEntity = discount_DishesJPA.findById(id)
                .orElseThrow(() -> new ResourceNoFoundException("Discount_Dishes not found with id: " + id));
        return Discount_DishesMapper.toDTO(discount_DishEntity);
    }

    public Discount_DishesDTO updateDiscount_Dishes(int id, Discount_DishesDTO discount_DishesDTO) {
        DiscountDishEntity discount_DishEntity = discount_DishesJPA.findById(id)
                .orElseThrow(() -> new ResourceNoFoundException("Discount_Dishes not found with id: " + id));
        discount_DishEntity = discount_DishesJPA.save(discount_DishEntity);
        return Discount_DishesMapper.toDTO(discount_DishEntity);
    }
}