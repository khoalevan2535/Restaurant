package com.poly.goldenbamboo.controllers;

import com.poly.goldenbamboo.dtos.DiscountCombodtos;
import com.poly.goldenbamboo.entities.DiscountComboEntity;
import com.poly.goldenbamboo.services.DiscountComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/Discount-Combo")
public class DiscountComboController {

    @Autowired
    private DiscountComboService discountComboService;

    @GetMapping
    public ResponseEntity<List<DiscountCombodtos>> getAll() {
        List<DiscountCombodtos> discountCombos = discountComboService.getAll();
        return ResponseEntity.ok(discountCombos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            DiscountComboEntity discountCombo = discountComboService.getById(id);
            return ResponseEntity.ok(discountCombo);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("Discount Combo không tồn tại.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy Discount Combo: " + e.getMessage());
        }
    }

    // Đổi path: Manager/Add
    @PostMapping("/Manager/Add")
    public ResponseEntity<?> create(@RequestBody DiscountCombodtos dto) {
        try {
            DiscountComboEntity created = discountComboService.create(dto);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi tạo Discount Combo: " + e.getMessage());
        }
    }

    @PutMapping("/Manager/Update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody DiscountCombodtos dto) {
        try {
            DiscountComboEntity updated = discountComboService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("Discount Combo không tồn tại.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật Discount Combo: " + e.getMessage());
        }
    }

    @DeleteMapping("/Manager/Delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            discountComboService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Discount Combo không tồn tại.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi xóa Discount Combo: " + e.getMessage());
        }
    }
}
