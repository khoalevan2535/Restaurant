package com.poly.goldenbamboo.dtos;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailDTO {
	private int id;
	private int orderId;
    private int dishOrComboId;
    private BigDecimal price;
    private int quantity;
    private boolean type; 
    private BigDecimal discountPercentage;
    private String name;
    private String image;
    private String description;
    private int branchId;
}
