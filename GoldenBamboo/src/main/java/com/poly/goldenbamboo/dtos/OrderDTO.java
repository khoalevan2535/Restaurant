package com.poly.goldenbamboo.dtos;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class OrderDTO {
    private int id;
    private Timestamp orderDate;
    private String paymentMethod;
    private BigDecimal prepay;
    private boolean status;
    private BigDecimal totalAmount;

    private int accountId;
    private int branchId;
    private int tableId;
}