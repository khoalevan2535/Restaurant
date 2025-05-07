package com.poly.goldenbamboo.mappers;

import com.poly.goldenbamboo.dtos.TableDTO;
import com.poly.goldenbamboo.entities.TableEntity;

public class TableMapper {
 public static TableDTO toDTO(TableEntity entity) {
     TableDTO dto = new TableDTO();
     dto.setId(entity.getId());
     dto.setNumber(entity.getNumber());
     dto.setStatus(entity.isStatus());
     dto.setBranchId(entity.getBranch().getId());
     return dto;
 }

 public static TableEntity toEntity(TableDTO dto) {
     TableEntity entity = new TableEntity();
     entity.setId(dto.getId());
     entity.setNumber(dto.getNumber());
     entity.setStatus(dto.isStatus());
     return entity;
 }
}
