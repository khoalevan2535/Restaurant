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
public class DiscountCombodtos {
	
	private Integer id;
    private Integer comboId;
    private Integer discountId;
    private BigDecimal discountPercentage;
}