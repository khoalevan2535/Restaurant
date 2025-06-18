package com.poly.goldenbamboo.mappers;

import com.poly.goldenbamboo.dtos.DiscountCombodtos;
import com.poly.goldenbamboo.entities.DiscountComboEntity;

public class Discount_CombooMapper {
    public static DiscountCombodtos toDTO(DiscountComboEntity entity) {
        if (entity == null) {
            return null;
        }
        DiscountCombodtos dto = new DiscountCombodtos();
        dto.setId(entity.getId());
        dto.setComboId(entity.getCombo().getId());
        dto.setDiscountId(entity.getDiscount().getId());
        dto.setDiscountPercentage(entity.getDiscountPercentage());
        return dto;
    }

    public static DiscountComboEntity toEntity(DiscountCombodtos dto) {
        if (dto == null) {
            return null;
        }
        DiscountComboEntity entity = new DiscountComboEntity();
        entity.setId(dto.getId());
        entity.setCombo(CombooMapper.toEntityById(dto.getComboId()));
        entity.setDiscount(DiscountMapper.mapDiscountEntityById(dto.getDiscountId()));
        entity.setDiscountPercentage(dto.getDiscountPercentage());
        return entity;
    }
}
