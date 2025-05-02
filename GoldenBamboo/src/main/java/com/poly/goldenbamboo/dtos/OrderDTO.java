package com.poly.goldenbamboo.dtos;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
	private int id;
	private int accountId;
	private int branchId;
//	private AccountDTO accountDTO;
//	private BranchDTO branchDTO;
	private BigDecimal totalAmount;
}
