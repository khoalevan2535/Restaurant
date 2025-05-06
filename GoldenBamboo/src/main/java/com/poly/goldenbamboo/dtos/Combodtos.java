package com.poly.goldenbamboo.dtos;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Combodtos {

    private Integer id;

    @NotBlank(message = "Tên combo không được để trống")
    @Size(max = 100, message = "Tên combo phải nhỏ hơn 100 ký tự")
    private String name;

    @Size(max = 500, message = "Mô tả combo phải nhỏ hơn 500 ký tự")
    private String description;

    @NotNull(message = "Giá combo không được để trống")
    @DecimalMin(value = "0.01", message = "Giá combo phải lớn hơn 0")
    private BigDecimal price;

    @NotNull(message = "Trạng thái không được để trống")
    private Boolean status;
}
