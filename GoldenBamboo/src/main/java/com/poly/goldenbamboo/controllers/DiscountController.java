package com.poly.goldenbamboo.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.poly.goldenbamboo.entities.DiscountEntity;
import com.poly.goldenbamboo.services.DiscountService;

@RestController
@RequestMapping("/Discount")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @GetMapping("/search")
    public ResponseEntity<List<DiscountEntity>> searchByName(
            @RequestParam(required = false) String name) {
        return ResponseEntity.ok(discountService.findByName(name));
    }

    @GetMapping
    public ResponseEntity<List<DiscountEntity>> getAllDiscount() {
        List<DiscountEntity> discounts = discountService.getAllDiscount();
        return ResponseEntity.ok(discounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDiscountById(@PathVariable int id) {
        try {
            DiscountEntity discount = discountService.getDiscountById(id);
            return ResponseEntity.ok(discount);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("Khuyến mãi không tồn tại.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi lấy khuyến mãi: " + e.getMessage());
        }
    }
    
    @PostMapping("/Manager/Discount/Add")
    public ResponseEntity<?> createDiscount(@RequestBody DiscountEntity discount) {
        try {
            DiscountEntity createdDiscount = discountService.createDiscount(discount);
            return ResponseEntity.ok(createdDiscount);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi tạo khuyến mãi: " + e.getMessage());
        }
    }

    @PutMapping("/Manager/Discount/Update/{id}")
    public ResponseEntity<?> updateDiscount(@PathVariable int id, @RequestBody DiscountEntity discount) {
        try {
            DiscountEntity updatedDiscount = discountService.updateDiscount(id, discount);
            return ResponseEntity.ok(updatedDiscount);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("Khuyến mãi không tồn tại.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi cập nhật khuyến mãi: " + e.getMessage());
        }
    }

    @DeleteMapping("/Manager/Discount/Delete/{id}")
    public ResponseEntity<?> deleteDiscount(@PathVariable int id) {
        try {
            discountService.deleteDiscount(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Khuyến mãi không tồn tại.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi xóa khuyến mãi: " + e.getMessage());
        }
    }
}
