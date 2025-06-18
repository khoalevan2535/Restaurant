package com.poly.goldenbamboo.dtos;

import java.util.List;

import com.poly.goldenbamboo.entities.BranchEntity;
import com.poly.goldenbamboo.entities.OrderEntity;
import com.poly.goldenbamboo.entities.ReservationEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableDTO {

	private int id;

	private int number;

	private int status;

	private int branchId;
	private int quantity;
}
