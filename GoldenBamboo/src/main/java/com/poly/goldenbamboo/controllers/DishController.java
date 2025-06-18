package com.poly.goldenbamboo.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.poly.goldenbamboo.dtos.CategoryDTO;
import com.poly.goldenbamboo.dtos.DishesDTO;
import com.poly.goldenbamboo.dtos.FoodDTO;
import com.poly.goldenbamboo.entities.DishEntity;
import com.poly.goldenbamboo.services.CloudinaryService;
import com.poly.goldenbamboo.services.DishService;

@RestController
@RequestMapping("/api/dishes")
public class DishController {
	@Autowired
    DishService dishesService;

    @Autowired
    private CloudinaryService cloudinaryService; // Inject CloudinaryService if needed

    @Value("${file.upload-dir:uploads}") // Có thể cấu hình trong application.properties
    private String uploadDir;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createDishesWithParams(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price,
            @RequestParam int categoryId,
            @RequestParam MultipartFile image) {
        try {
            // Sử dụng CloudinaryService để upload hình ảnh
            String secureUrl = cloudinaryService.uploadFile(image);

            // Tạo và thiết lập đối tượng DishesBeans
            DishesDTO dish = new DishesDTO();
            dish.setName(name);
            dish.setDescription(description);
            dish.setPrice(price);
            dish.setImage(secureUrl); // Lưu lại URL hình từ Cloudinary

            dish.setCategoryId(categoryId);
            CategoryDTO category = new CategoryDTO();
            category.setId(categoryId);
            // Nếu cần thiết, gán thêm dữ liệu cho category

            DishesDTO createdDish = dishesService.createDishes(dish, null);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDish);

        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body("Error creating dish: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<DishesDTO>> getAllDishes() {
        List<DishesDTO> dishes = dishesService.getDishesWithDiscountFlag();
        return ResponseEntity.ok(dishes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishesDTO> getDishesById(@PathVariable("id") int dishesId) {
        DishesDTO dishes = dishesService.getDishesById(dishesId);
        return ResponseEntity.ok(dishes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDishes(@PathVariable("id") int dishesId) {
        dishesService.deleteDishes(dishesId);
        return ResponseEntity.ok("Dishes deleted successfully");
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateDishesPatch(
            @PathVariable("id") Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) MultipartFile image) {

                DishesDTO dish = dishesService.getDishesById(id);
        if (dish == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            if (name != null) {
                dish.setName(name);
            }
            if (description != null) {
                dish.setDescription(description);
            }
            if (price != null) {
                dish.setPrice(price);
            }
            if (categoryId != null) {
                dish.setCategoryId(categoryId);
            }

            if (image != null && !image.isEmpty()) {
                // Nếu cần, xóa ảnh cũ trên Cloudinary
                if (dish.getImage() != null) {
                    String publicId = cloudinaryService.extractPublicIdFromUrl(dish.getImage());
                    if (publicId != null) {
                        cloudinaryService.deleteFile(publicId);
                    }
                }
                String secureUrl = cloudinaryService.uploadFile(image);
                dish.setImage(secureUrl);
            }

            DishesDTO updatedDish = dishesService.updateDishes(id, dish, null);
            return ResponseEntity.ok(updatedDish);

        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body("Error updating dish: " + e.getMessage());
        }
    }
}