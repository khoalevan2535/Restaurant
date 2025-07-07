package com.poly.goldenbamboo.controllers;

import com.poly.goldenbamboo.dtos.ComboDishFullDTO;
import com.poly.goldenbamboo.services.ComboDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/combodish")
public class ComboDishController {

    @Autowired
    private ComboDishService comboDishService;

    // Lấy tất cả combo dish
    @GetMapping
    public ResponseEntity<List<ComboDishFullDTO>> getAllComboDishes() {
        List<ComboDishFullDTO> result = comboDishService.getComboDishFullDTOs();
        return ResponseEntity.ok(result);
    }

    // Tạo mới combo dish
    @PostMapping
    public ResponseEntity<?> createComboDish(@RequestBody ComboDishFullDTO request) {
        try {
            ComboDishFullDTO created = comboDishService.createComboDish(
                    request.getComboId(),
                    request.getDish1Id(),
                    request.getDish2Id()
            );
            return ResponseEntity.ok(created);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi tạo ComboDish: " + e.getMessage());
        }
    }

    // Cập nhật combo dish theo id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateComboDish(@PathVariable int id, @RequestBody ComboDishFullDTO request) {
        try {
            ComboDishFullDTO updated = comboDishService.updateComboDish(
                    id,
                    request.getComboId(),
                    request.getDish1Id(),
                    request.getDish2Id()
            );
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("ComboDish không tồn tại.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật ComboDish: " + e.getMessage());
        }
    }

    // Xóa combo dish theo id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComboDish(@PathVariable int id) {
        try {
            comboDishService.deleteComboDishByDishId(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("ComboDish không tồn tại.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi xóa ComboDish: " + e.getMessage());
        }
    }
}
