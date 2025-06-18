package com.poly.goldenbamboo.mappers;

import com.poly.goldenbamboo.dtos.Combodtos;
import com.poly.goldenbamboo.entities.ComboEntity;

public class CombooMapper {
    public static Combodtos toDTO(ComboEntity entity) {
        if (entity == null) {
            return null;
        }
        Combodtos dto = new Combodtos();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setImage(entity.getImage());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        return dto;
    }

    public static ComboEntity toEntity(Combodtos dto) {
        if (dto == null) {
            return null;
        }
        ComboEntity entity = new ComboEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setImage(dto.getImage());

        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        return entity;
    }

    public static ComboEntity toEntityById(Integer id) {
        if (id == null) {
            return null;
        }
        ComboEntity entity = new ComboEntity();
        entity.setId(id);
        return entity;
    }
}
