package com.poly.goldenbamboo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException; 

import com.poly.goldenbamboo.entities.DiscountEntity;
import com.poly.goldenbamboo.repositories.DiscountJPA;

@Service
public class DiscountService {

    @Autowired
    DiscountJPA discountJPA;

    public List<DiscountEntity> getAllDiscount() {
        return discountJPA.findAll();
    }

    public List<DiscountEntity> findByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return discountJPA.findAll();
        }
        return discountJPA.findByNameContainingIgnoreCase(keyword);
    }

    public DiscountEntity createDiscount(DiscountEntity discount) {
        if (discount.getName() == null || discount.getName().isEmpty()) {
            throw new IllegalArgumentException("Tên khuyến mãi là bắt buộc");
        }
        if (discount.getPercentage() < 0 || discount.getPercentage() > 100) {
            throw new IllegalArgumentException("Phần trăm giảm giá phải từ 0 đến 100");
        }
        // ✅ Đã xoá kiểm tra usageLimit
        return discountJPA.save(discount);
    }

    public DiscountEntity updateDiscount(int id, DiscountEntity discount) {
        DiscountEntity existingDiscount = discountJPA.findById(id)
                .orElseThrow(() -> new RuntimeException("Khuyến mãi không tồn tại"));

        existingDiscount.setName(discount.getName());
        existingDiscount.setStartDate(discount.getStartDate());
        existingDiscount.setEndDate(discount.getEndDate());
        existingDiscount.setStatus(discount.isStatus());
        existingDiscount.setPercentage(discount.getPercentage());

        return discountJPA.save(existingDiscount);
    }

    public void deleteDiscount(int id) {
        if (!discountJPA.existsById(id)) {
            throw new RuntimeException("Khuyến mãi không tồn tại");
        }
        discountJPA.deleteById(id);
    }

    public DiscountEntity getDiscountById(int id) {
        return discountJPA.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Không tìm thấy khuyến mãi"));
    }

}
