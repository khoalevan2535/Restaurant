package com.poly.goldenbamboo.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "dishes") // Tên bảng đã đúng theo quy ước (chữ thường, số nhiều)
public class DishEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Size(max = 1000, message = "Mô tả không được vượt quá 1000 ký tự")
    @Column(name = "description")
    private String description;

    @Size(max = 255, message = "Đường dẫn ảnh không được vượt quá 255 ký tự")
    @Column(name = "image")
    private String image; 
    
    @NotBlank(message = "Tên món ăn không được để trống")
    @Size(max = 100, message = "Tên món ăn không được vượt quá 100 ký tự")
    @Column(name = "name", nullable = false)
    private String name;

    @PositiveOrZero(message = "Giá phải lớn hơn hoặc bằng 0")
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "status", nullable = false)
    private boolean status; // Trạng thái hoạt động của món ăn

    // Liên kết với ComboDishEntity
    @OneToMany(mappedBy = "dish", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ComboDishEntity> comboDishes = new ArrayList<>(); // Danh sách combo chứa món ăn

    // Liên kết với DiscountDishEntity
    @OneToMany(mappedBy = "dish", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<DiscountDishEntity> discountDishes = new ArrayList<>(); // Danh sách mã giảm giá áp dụng

    // Liên kết với CategoryEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false) // Khóa ngoại, không được null
    @JsonIgnore
    private CategoryEntity category;

    // Liên kết với MenuDishEntity
    @OneToMany(mappedBy = "dish", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<MenuDishEntity> menuDishes = new ArrayList<>(); // Danh sách menu chứa món ăn

}