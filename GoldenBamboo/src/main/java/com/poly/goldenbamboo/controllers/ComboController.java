package com.poly.goldenbamboo.controllers;

import com.poly.goldenbamboo.entities.ComboEntity;
import com.poly.goldenbamboo.repositories.ComboJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/Manager/Combo")
@CrossOrigin(origins = "http://localhost:3000")
public class ComboController {

    @Autowired
    private ComboJPA comboJPA;

    @Value("${file.upload-dir:uploads}") // Có thể cấu hình trong application.properties
    private String uploadDir;

    @GetMapping
    public ResponseEntity<List<ComboEntity>> getAllCombos() {
        return ResponseEntity.ok(comboJPA.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComboEntity> getComboById(@PathVariable Integer id) {
        return comboJPA.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> createCombo(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price,
            @RequestParam String status,
            @RequestParam MultipartFile image) {
        
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = generateFileName(image.getOriginalFilename());
            Files.copy(image.getInputStream(), uploadPath.resolve(fileName));

            ComboEntity combo = new ComboEntity();
            combo.setName(name);
            combo.setDescription(description);
            combo.setPrice(price);
            combo.setStatus("active".equals(status));
            combo.setImage(fileName); // Lưu tên file thay vì đường dẫn đầy đủ

            ComboEntity savedCombo = comboJPA.save(combo);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCombo);
            
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body("Error creating combo: " + e.getMessage());
        }
    }

    @PatchMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> updateCombo(
            @PathVariable Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) MultipartFile image) {
        
        return comboJPA.findById(id)
                .map(combo -> {
                    try {
                        if (name != null) combo.setName(name);
                        if (description != null) combo.setDescription(description);
                        if (price != null) combo.setPrice(price);
                        if (status != null) combo.setStatus("active".equals(status));

                        if (image != null && !image.isEmpty()) {
                            // Xóa ảnh cũ nếu tồn tại
                            if (combo.getImage() != null) {
                                deleteImageFile(combo.getImage());
                            }
                            
                            String fileName = generateFileName(image.getOriginalFilename());
                            Path uploadPath = Paths.get(uploadDir);
                            Files.copy(image.getInputStream(), uploadPath.resolve(fileName));
                            combo.setImage(fileName);
                        }

                        ComboEntity updatedCombo = comboJPA.save(combo);
                        return ResponseEntity.ok(updatedCombo);
                        
                    } catch (IOException e) {
                        return ResponseEntity.internalServerError()
                                .body("Error updating combo: " + e.getMessage());
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCombo(@PathVariable Integer id) {
        return comboJPA.findById(id)
                .map(combo -> {
                    // Xóa ảnh trước khi xóa combo
                    if (combo.getImage() != null) {
                        deleteImageFile(combo.getImage());
                    }
                    comboJPA.delete(combo);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private String generateFileName(String originalFileName) {
        return UUID.randomUUID() + "_" + originalFileName;
    }

    private void deleteImageFile(String fileName) {
        try {
            Path filePath = Paths.get(uploadDir, fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // Log lỗi nhưng không làm gián đoạn flow chính
            System.err.println("Error deleting image file: " + e.getMessage());
        }
    }
}