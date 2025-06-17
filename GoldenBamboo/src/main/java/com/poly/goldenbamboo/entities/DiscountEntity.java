package com.poly.goldenbamboo.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "discounts") // Tên bảng đã đúng theo quy ước (chữ thường, số nhiều)
public class DiscountEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "Tên mã giảm giá không được để trống")
    @Size(max = 100, message = "Tên mã giảm giá không được vượt quá 100 ký tự")
    @Column(name = "name", nullable = false)
    private String name;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate; // Sử dụng LocalDate thay vì String để xử lý ngày tháng

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate; // Sử dụng LocalDate thay vì String

    @Column(name = "status", nullable = false)
    private boolean status; // Trạng thái hoạt động của mã giảm giá

    // Liên kết với DiscountComboEntity
    @OneToMany(mappedBy = "discount", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<DiscountComboEntity> discountCombos = new ArrayList<>(); // Danh sách combo áp dụng mã giảm giá

    // Liên kết với DiscountDishEntity
    @OneToMany(mappedBy = "discount", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<DiscountDishEntity> discountDishes = new ArrayList<>(); // Danh sách món ăn áp dụng mã giảm giá
}