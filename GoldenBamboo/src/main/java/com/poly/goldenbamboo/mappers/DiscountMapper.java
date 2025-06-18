package com.poly.goldenbamboo.mappers;

import com.poly.goldenbamboo.entities.DiscountEntity;

public class DiscountMapper {
    public static DiscountEntity mapDiscountEntityById(Integer id) {
        if (id == null) {
            return null;
        }
        DiscountEntity discountEntity = new DiscountEntity();
        discountEntity.setId(id);
        return discountEntity;
    }
}
