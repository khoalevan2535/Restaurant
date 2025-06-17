package com.poly.goldenbamboo.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orders") // Tên bảng đã đúng theo quy ước (chữ thường, số nhiều)
public class OrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @CreationTimestamp // Tự động điền thời gian tạo đơn hàng
    @Column(name = "order_date", nullable = false, updatable = false)
    private Timestamp orderDate;

    @NotBlank(message = "Phương thức thanh toán không được để trống")
    @Size(max = 50, message = "Phương thức thanh toán không được vượt quá 50 ký tự")
    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @PositiveOrZero(message = "Tiền cọc phải lớn hơn hoặc bằng 0")
    @Column(name = "prepay", nullable = false, precision = 10, scale = 2)
    private BigDecimal prepay;

    @Column(name = "status", nullable = false)
    private int status; 
    
    @Size(max = 1000, message = "Mô tả không được vượt quá 1000 ký tự")
    @Column(name = "description", nullable = false)
    private String description;

    @PositiveOrZero(message = "Tổng tiền phải lớn hơn hoặc bằng 0")
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    // Liên kết với OrderDetailEntity
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<OrderDetailEntity> orderDetails = new ArrayList<>(); // Danh sách chi tiết đơn hàng

    // Liên kết với AccountEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false) // Khóa ngoại, không được null
    @NotNull(message = "Tài khoản không được để trống")
    @JsonIgnore
    private AccountEntity account;

    // Liên kết với BranchEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false) // Khóa ngoại, không được null
    @NotNull(message = "Chi nhánh không được để trống")
    @JsonIgnore
    private BranchEntity branch;

    // Liên kết với TableEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id", nullable = true) // Có thể để null nếu đơn hàng không gắn với bàn
    @JsonIgnore
    private TableEntity table;
}