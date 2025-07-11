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
    private int status;
    private BigDecimal totalAmount;
    private String description;

    private int accountId;
    private int branchId;
    private int tableId;
}