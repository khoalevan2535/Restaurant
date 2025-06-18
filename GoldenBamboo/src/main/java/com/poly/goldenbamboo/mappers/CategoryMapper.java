package com.poly.goldenbamboo.mappers;

import com.poly.goldenbamboo.dtos.CategoryDTO;
import com.poly.goldenbamboo.entities.CategoryEntity;

public class CategoryMapper {
    
    public static CategoryDTO mapToCategoryBeans(CategoryEntity categoryEntity){
        return new CategoryDTO(
                categoryEntity.getId(),
                categoryEntity.getName()
        );
    }

    public static CategoryEntity mapToCategoryEntity(CategoryDTO categoryBeans){
        return new CategoryEntity(
                categoryBeans.getId(),
                categoryBeans.getName()
        );
    }

    public static CategoryEntity mapToCategoryEntityById(Integer id) {
        if (id == null) {
            return null;
        }
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(id);
        return categoryEntity;
    }
}
