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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "combos") // Tên bảng đã đúng theo quy ước (chữ thường, số nhiều)
public class ComboEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Size(max = 1000, message = "Mô tả không được vượt quá 1000 ký tự")
    @Column(name = "description")
    private String description; // Có thể để null nếu không bắt buộc

    @Size(max = 255, message = "Đường dẫn ảnh không được vượt quá 255 ký tự")
    @Column(name = "image")
    private String image; // Đường dẫn hoặc tên file ảnh, có thể để null

    @NotBlank(message = "Tên combo không được để trống")
    @Size(max = 100, message = "Tên combo không được vượt quá 100 ký tự")
    @Column(name = "name", nullable = false)
    private String name;

    @PositiveOrZero(message = "Giá phải lớn hơn hoặc bằng 0")
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "status", nullable = false)
    private boolean status; // Trạng thái hoạt động của combo

    // Liên kết với ComboDishEntity
    @OneToMany(mappedBy = "combo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ComboDishEntity> comboDishes = new ArrayList<>(); // Danh sách món ăn trong combo

    // Liên kết với DiscountComboEntity
    @OneToMany(mappedBy = "combo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<DiscountComboEntity> discountCombos = new ArrayList<>(); // Danh sách giảm giá áp dụng

    // Liên kết với MenuComboEntity
    @OneToMany(mappedBy = "combo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<MenuComboEntity> menuCombos = new ArrayList<>(); // Danh sách menu chứa combo

}