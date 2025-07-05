package com.poly.goldenbamboo.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "discounts") 
public class DiscountEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Tên mã giảm giá không được để trống")
    @Size(max = 100, message = "Tên mã giảm giá không được vượt quá 100 ký tự")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Tỷ lệ giảm giá không được để trống")
    @Min(value = 0, message = "Tỷ lệ giảm giá phải >= 0")
    @Max(value = 100, message = "Tỷ lệ giảm giá phải <= 100")
    @Column(name = "percentage", nullable = false)
    private Integer percentage;  

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private boolean status;

    @OneToMany(mappedBy = "discount", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<DiscountComboEntity> discountCombos = new ArrayList<>();

    @OneToMany(mappedBy = "discount", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<DiscountDishEntity> discountDishes = new ArrayList<>();
}
