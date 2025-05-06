package com.poly.goldenbamboo.mappers;

import org.springframework.stereotype.Component;

import com.poly.goldenbamboo.dtos.AccountDTO;
import com.poly.goldenbamboo.dtos.RoleDTO;
import com.poly.goldenbamboo.entities.AccountEntity;
import com.poly.goldenbamboo.entities.RoleEntity;

@Component
public class AccountMapper {

	public AccountDTO toDTO(AccountEntity entity) {
		AccountDTO dto = new AccountDTO();
		dto.setId(entity.getId());
		dto.setPhone(entity.getPhone());
		dto.setPassword(entity.getPassword());
		dto.setRole(String.valueOf(entity.getRole().getId()));

		return dto;
	}

	public AccountEntity toEntity(AccountDTO dto) {
		AccountEntity entity = new AccountEntity();
		entity.setId(dto.getId());
		entity.setPhone(dto.getPhone());
		entity.setPassword(dto.getPassword());

		RoleEntity role = new RoleEntity();
		role.setId(Integer.parseInt(dto.getRole()));
		entity.setRole(role);

		return entity;
	}

}
