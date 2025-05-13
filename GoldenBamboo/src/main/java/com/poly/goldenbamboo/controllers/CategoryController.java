package com.poly.goldenbamboo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.goldenbamboo.services.CategoryService;

@RestController
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/Category")
    public ResponseEntity<?> findAllCategory() {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }
}
