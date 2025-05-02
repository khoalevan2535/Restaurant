package com.poly.goldenbamboo.mappers;

import org.springframework.stereotype.Component;

import com.poly.goldenbamboo.dtos.BranchDTO;
import com.poly.goldenbamboo.entities.BranchEntity;

@Component
public class BranchMapper {
	public BranchEntity toEntity(BranchDTO dto) {
	    BranchEntity entity = new BranchEntity();
	    entity.setId(dto.getId());
	    entity.setName(dto.getName());
	    entity.setAddress(dto.getAddress());
	    entity.setDescription(dto.getDescription());
	    entity.setParentId(dto.getParentId());
	    entity.setStatus(dto.isStatus());
	    return entity;
	}

	public BranchDTO toDTO(BranchEntity entity) {
	    BranchDTO dto = new BranchDTO();
	    dto.setId(entity.getId());
	    dto.setName(entity.getName());
	    dto.setAddress(entity.getAddress());
	    dto.setDescription(entity.getDescription());
	    dto.setParentId(entity.getParentId());
	    dto.setStatus(entity.isStatus());
	    return dto;
	}


}
