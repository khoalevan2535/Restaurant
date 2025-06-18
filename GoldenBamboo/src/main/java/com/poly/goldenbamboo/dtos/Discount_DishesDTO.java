package com.poly.goldenbamboo.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Discount_DishesDTO {
        private int discountId;
        private int dishId;
        private BigDecimal discountPercentage;
}
