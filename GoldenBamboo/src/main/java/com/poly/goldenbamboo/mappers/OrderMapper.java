package com.poly.goldenbamboo.mappers;

import org.springframework.stereotype.Component;

import com.poly.goldenbamboo.dtos.OrderDTO;
import com.poly.goldenbamboo.entities.AccountEntity;
import com.poly.goldenbamboo.entities.BranchEntity;
import com.poly.goldenbamboo.entities.OrderEntity;

@Component
public class OrderMapper {

	public OrderDTO toDTO(OrderEntity entity) {
		if (entity == null)
			return null;
		OrderDTO dto = new OrderDTO();
		dto.setId(entity.getId());
		dto.setAccountId(entity.getAccount().getId());
		dto.setBranchId(entity.getBranch().getId());
		dto.setTotalAmount(entity.getTotalAmount());
		return dto;
	}

	public OrderEntity toEntity(OrderDTO dto) {
		if (dto == null)
			return null;

		OrderEntity entity = new OrderEntity();
		entity.setId(dto.getId());

		AccountEntity account = new AccountEntity();
		account.setId(dto.getAccountId());
		entity.setAccount(account);

		BranchEntity branch = new BranchEntity();
		branch.setId(dto.getBranchId());
		entity.setBranch(branch);
		entity.setTotalAmount(dto.getTotalAmount());

		return entity;
	}

}
