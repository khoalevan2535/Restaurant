package com.poly.goldenbamboo.entities;

import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "menu_dishes") // Tên bảng theo quy ước (chữ thường, số nhiều)
public class MenuDishEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Sửa lỗi typo
    @Column(name = "id")
    private int id;

    // Liên kết với DishEntity
    @ManyToOne(fetch = FetchType.LAZY) // Tải lười để tối ưu hiệu suất
    @JoinColumn(name = "dish_id", nullable = false) // Khóa ngoại, không được null
    @NotNull(message = "Món ăn không được để trống")
    @JsonIgnore
    private DishEntity dish; // Sửa tên biến từ 'dishe' thành 'dish' cho đúng ngữ nghĩa

    // Liên kết với MenuEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false) // Khóa ngoại, không được null
    @NotNull(message = "Menu không được để trống")
    @JsonIgnore
    private MenuEntity menu; // Sửa tên biến từ 'menus' thành 'menu' cho đúng ngữ nghĩa
}