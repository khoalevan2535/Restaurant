package com.poly.goldenbamboo.mappers;

import com.poly.goldenbamboo.dtos.DishesDTO;
import com.poly.goldenbamboo.entities.DishEntity;

public class DishesMapper {

    // Chuyển từ DishesEntity sang DishesBeans
    public static DishesDTO mapToDishesBeans(DishEntity dishesEntity) {
        if (dishesEntity == null) {
            return null;
        }
        DishesDTO dishesBeans = new DishesDTO();
        dishesBeans.setId(dishesEntity.getId());
        // Nếu đối tượng Category != null thì lấy mã danh mục, ngược lại để null
        dishesBeans.setCategoryId(
            dishesEntity.getCategory() != null ? dishesEntity.getCategory().getId() : null
        );
        dishesBeans.setName(dishesEntity.getName());
        dishesBeans.setPrice(dishesEntity.getPrice());
        dishesBeans.setDescription(dishesEntity.getDescription());
        dishesBeans.setFinalPrice(dishesEntity.getPrice());
        dishesBeans.setImage(dishesEntity.getImage());
        return dishesBeans;
    }
    
    // Chuyển từ DishesBeans sang DishesEntity
    public static DishEntity mapToDishesEntity(DishesDTO dishesBeans) {
        if (dishesBeans == null) {
            return null;
        }
        DishEntity dishesEntity = new DishEntity();
        dishesEntity.setId(dishesBeans.getId());
        // Sử dụng CategoryMapper để ánh xạ từ categoryId sang đối tượng CategoryEntity.
        // Giả sử CategoryMapper có phương thức mapToCategoryEntityById(Integer id)
        dishesEntity.setCategory(CategoryMapper.mapToCategoryEntityById(dishesBeans.getCategoryId()));
        dishesEntity.setName(dishesBeans.getName());
        dishesEntity.setPrice(dishesBeans.getPrice());
        dishesEntity.setDescription(dishesBeans.getDescription());
        dishesEntity.setImage(dishesBeans.getImage());
        return dishesEntity;
    }

    public static DishEntity mapToDishesEntityById(Integer id) {
        if (id == null) {
            return null;
        }
        DishEntity dishesEntity = new DishEntity();
        dishesEntity.setId(id);
        return dishesEntity;
    }

}