package com.poly.goldenbamboo.mappers;

import com.poly.goldenbamboo.dtos.Discount_DishesDTO;
import com.poly.goldenbamboo.entities.DiscountDishEntity;

public class Discount_DishesMapper {
    public static Discount_DishesDTO toDTO(DiscountDishEntity entity) {
        if (entity == null) {
            return null;
        }
        Discount_DishesDTO dto = new Discount_DishesDTO();
        dto.setDiscountId(entity.getDiscount().getId());
        dto.setDishId(entity.getDish().getId());
        dto.setDiscountPercentage(entity.getDiscountPercentage());
        return dto;
    }

    public static DiscountDishEntity toEntity(Discount_DishesDTO dto) {
        if (dto == null) {
            return null;
        }
        DiscountDishEntity entity = new DiscountDishEntity();
        entity.setDish(DishesMapper.mapToDishesEntityById(dto.getDishId()));
        entity.setDiscount(DiscountMapper.mapDiscountEntityById(dto.getDiscountId()));
        entity.setDiscountPercentage(dto.getDiscountPercentage());
        return entity;
    }
}
 