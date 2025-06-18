package com.poly.goldenbamboo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.goldenbamboo.dtos.CategoryDTO;
import com.poly.goldenbamboo.services.CategoryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
    CategoryService categoryService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryBeans) {
        CategoryDTO createdCategory = categoryService.createCategory(categoryBeans);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
        // return ResponseEntity.ok(categoryBeans);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") int categoryId) {
        CategoryDTO category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("{id}")
    // @PreAuthorize("hasRole('ADMIN')") // Chỉ cho phép người dùng có quyền ADMIN thực hiện yêu cầu này
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable("id") int categoryId, @RequestBody CategoryDTO categoryBeans) {
        CategoryDTO updatedCategory = categoryService.updateCategory(categoryId, categoryBeans);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") int categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Deleted successfully.");
    }
}
