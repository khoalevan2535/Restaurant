package com.poly.goldenbamboo.entities;

import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "reservation_details") // Tên bảng theo quy ước (chữ thường, số nhiều)
public class ReservationDetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "dish_or_combo_id", nullable = false)
    @Positive(message = "ID món ăn hoặc combo phải lớn hơn 0")
    private int dishOrComboId; // ID của món ăn hoặc combo

    @Column(name = "quantity", nullable = false)
    @Positive(message = "Số lượng phải lớn hơn 0")
    private int quantity;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemType type; // Loại: món ăn (DISH) hoặc combo (COMBO)

    // Liên kết với ReservationEntity
    @ManyToOne(fetch = FetchType.LAZY) // Tải lười để tối ưu hiệu suất
    @JoinColumn(name = "reservation_id", nullable = false) // Khóa ngoại, không được null
    @NotNull(message = "Đặt bàn không được để trống")
    @JsonIgnore
    private ReservationEntity reservation;

    // Enum để biểu thị loại món ăn hoặc combo
    public enum ItemType {
        DISH, COMBO
    }
}