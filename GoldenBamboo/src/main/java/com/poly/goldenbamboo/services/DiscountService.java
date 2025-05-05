package com.poly.goldenbamboo.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        return discountJPA.save(discount);
    }

    public DiscountEntity updateDiscount(int id, DiscountEntity discount) {
        DiscountEntity existingDiscount = discountJPA.findById(id)
                .orElseThrow(() -> new RuntimeException("Khuyến mãi không tồn tại"));
        existingDiscount.setName(discount.getName());
        existingDiscount.setStartDate(discount.getStartDate());
        existingDiscount.setEndDate(discount.getEndDate());
        existingDiscount.setStatus(discount.isStatus());
        return discountJPA.save(existingDiscount);
    }

    public void deleteDiscount(int id) {
        discountJPA.deleteById(id);
    }
}
