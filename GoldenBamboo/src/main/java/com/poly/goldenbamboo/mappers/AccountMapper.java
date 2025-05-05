package com.poly.goldenbamboo.mappers;

import org.springframework.stereotype.Component;

import com.poly.goldenbamboo.dtos.AccountDTO;
import com.poly.goldenbamboo.entities.AccountEntity;

@Component
public class AccountMapper {

    public AccountDTO toDTO(AccountEntity entity) {
        AccountDTO dto = new AccountDTO();
        dto.setId(entity.getId());
        dto.setPhone(entity.getPhone());
        dto.setPassword(entity.getPassword());
        return dto;
    }

    public AccountEntity toEntity(AccountDTO dto) {
        AccountEntity entity = new AccountEntity();
        entity.setId(dto.getId());
        entity.setPhone(dto.getPhone());
        entity.setPassword(dto.getPassword());
        return entity;
    }
}
