package com.poly.goldenbamboo.controllers;

import com.poly.goldenbamboo.entities.ComboEntity;
import com.poly.goldenbamboo.services.ComboService;
import com.poly.goldenbamboo.services.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/Combo")
public class ComboController {

    @Autowired
    private ComboService comboService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping
    public ResponseEntity<List<ComboEntity>> getAllCombos() {
        List<ComboEntity> combos = comboService.getAllCombo();
        return ResponseEntity.ok(combos);
    }

    @GetMapping("/Search")
    public ResponseEntity<List<ComboEntity>> searchCombos(@RequestParam String keyword) {
        List<ComboEntity> combos = comboService.searchCombos(keyword);
        return ResponseEntity.ok(combos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComboEntity> getComboById(@PathVariable Integer id) {
        ComboEntity combo = comboService.getComboById(id);
        return combo != null ? ResponseEntity.ok(combo) : ResponseEntity.notFound().build();
    }

    @PostMapping("/Manager/Combo/Add")
    public ResponseEntity<?> createCombo(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price,
            @RequestParam(value = "status", defaultValue = "true") boolean status, // Chuyển sang boolean
            @RequestParam MultipartFile image) {
        try {
            ComboEntity combo = new ComboEntity();
            combo.setName(name);
            combo.setDescription(description);
            combo.setPrice(price);
            combo.setStatus(status); // Sử dụng boolean trực tiếp

            String imageUrl = cloudinaryService.uploadFile(image);
            combo.setImage(imageUrl);

            ComboEntity savedCombo = comboService.createCombo(combo, image);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCombo);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating combo: " + e.getMessage());
        }
    }

    @PutMapping("/Manager/Combo/Update/{id}")
    public ResponseEntity<?> updateCombo(
            @PathVariable Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) Boolean status, 
            @RequestParam(required = false) MultipartFile image) {
        try {
            ComboEntity existingCombo = comboService.getComboById(id);
            if (existingCombo == null) {
                return ResponseEntity.notFound().build();
            }

            // Cập nhật các trường nếu có giá trị
            if (name != null && !name.isEmpty()) existingCombo.setName(name);
            if (description != null && !description.isEmpty()) existingCombo.setDescription(description);
            if (price != null) existingCombo.setPrice(price);
            if (status != null) existingCombo.setStatus(status); // Cập nhật status với bất kỳ giá trị nào

            // Xử lý ảnh nếu có
            if (image != null && !image.isEmpty()) {
                if (existingCombo.getImage() != null) {
                    cloudinaryService.deleteFile(cloudinaryService.extractPublicIdFromUrl(existingCombo.getImage()));
                }
                String newImageUrl = cloudinaryService.uploadFile(image);
                existingCombo.setImage(newImageUrl);
            }

            ComboEntity updatedCombo = comboService.updateCombo(id, existingCombo, image);
            return ResponseEntity.ok(updatedCombo);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating combo: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/Manager/Combo/Delete/{id}")
    public ResponseEntity<String> deleteCombo(@PathVariable Integer id) {
        try {
            comboService.deleteCombo(id);
            return ResponseEntity.ok("Xóa combo thành công!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting combo: " + e.getMessage());
        }
    }
}