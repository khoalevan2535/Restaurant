package com.poly.goldenbamboo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.goldenbamboo.dtos.CategoryDTO;
import com.poly.goldenbamboo.entities.CategoryEntity;
import com.poly.goldenbamboo.entities.DishEntity;
import com.poly.goldenbamboo.exception.ResourceNoFoundException;
import com.poly.goldenbamboo.mappers.CategoryMapper;
import com.poly.goldenbamboo.repositories.CategoryJPA;

@Service
public class CategoryService {
	@Autowired
     CategoryJPA categoryJPA;

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = CategoryMapper.mapToCategoryEntity(categoryDTO);
        CategoryEntity saveCa = categoryJPA.save(categoryEntity);

        return CategoryMapper.mapToCategoryBeans(saveCa);
    }

    public CategoryDTO getCategoryById(int categoryId) {

        CategoryEntity ca = categoryJPA.findById(categoryId)
                .orElseThrow(() -> new ResourceNoFoundException("Category not found with id: " + categoryId));
        return CategoryMapper.mapToCategoryBeans(ca);

    }

    public List<CategoryDTO> getAllCategories() {
        List<CategoryEntity> categories = categoryJPA.findAll();
        return categories.stream().map((category) -> CategoryMapper
        .mapToCategoryBeans(category))
        .collect(Collectors.toList()); 
    }

    public CategoryDTO updateCategory(int categoryId, CategoryDTO categoryBeans) {
        CategoryEntity categoryEntity = categoryJPA.findById(categoryId).orElseThrow(()
         -> new ResourceNoFoundException("Category not found with id: " + categoryId));

         categoryEntity.setName(categoryBeans.getName());
         CategoryEntity upCategoryEntity = categoryJPA.save(categoryEntity);
        return CategoryMapper.mapToCategoryBeans(upCategoryEntity); 
    }

    public void deleteCategory(int categoryId) {
        CategoryEntity categoryEntity = categoryJPA.findById(categoryId).orElseThrow(()
         -> new ResourceNoFoundException("Category not found with id: " + categoryId));
            // categoryJPA.deleteById(categoryId);
            categoryJPA.delete(categoryEntity);
    }
}
