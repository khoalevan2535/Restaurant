package com.poly.goldenbamboo.dtos;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class DishesDTO {
    
    private int id;

    private Integer categoryId;

    private String name;

    private BigDecimal price;

    private String description;

    private String image;

    // Thuộc tính dùng hiển thị số tiền còn lại sau giảm
    private BigDecimal finalPrice;
    // Thuộc tính dùng hiển thị cờ giảm giá (mặc dù không lưu xuống DB)
    private Boolean discounted;
}
