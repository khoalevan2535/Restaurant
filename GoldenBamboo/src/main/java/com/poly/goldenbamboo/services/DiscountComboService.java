package com.poly.goldenbamboo.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poly.goldenbamboo.dtos.DiscountCombodtos;
import com.poly.goldenbamboo.entities.ComboEntity;
import com.poly.goldenbamboo.entities.DiscountComboEntity;
import com.poly.goldenbamboo.entities.DiscountEntity;
import com.poly.goldenbamboo.repositories.ComboJPA;
import com.poly.goldenbamboo.repositories.DiscountComboJPA;
import com.poly.goldenbamboo.repositories.DiscountJPA;

@Service
public class DiscountComboService {

    private final DiscountComboJPA repository;
    private final ComboJPA comboJPA;
    private final DiscountJPA discountJPA;

    public DiscountComboService(DiscountComboJPA repository, 
                              ComboJPA comboJPA, 
                              DiscountJPA discountJPA) {
        this.repository = repository;
        this.comboJPA = comboJPA;
        this.discountJPA = discountJPA;
    }

    @Transactional(readOnly = true)
    public List<DiscountCombodtos> getAll() {
        return repository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public DiscountComboEntity create(DiscountCombodtos dto) {
        validateDto(dto);
        
        DiscountComboEntity entity = new DiscountComboEntity();
        entity.setCombo(comboJPA.findById(dto.getComboId())
                .orElseThrow(() -> new IllegalArgumentException("Combo không tồn tại")));
        entity.setDiscount(discountJPA.findById(dto.getDiscountId())
                .orElseThrow(() -> new IllegalArgumentException("Discount không tồn tại")));
        entity.setDiscountPercentage(dto.getDiscountPercentage());
        
        return repository.save(entity);
    }

    @Transactional
    public DiscountComboEntity update(Integer id, DiscountCombodtos dto) {
        validateDto(dto);
        
        DiscountComboEntity entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Discount combo không tồn tại"));
                
        ComboEntity combo = comboJPA.findById(dto.getComboId())
                .orElseThrow(() -> new IllegalArgumentException("Combo không tồn tại"));
        
        DiscountEntity discount = discountJPA.findById(dto.getDiscountId())
                .orElseThrow(() -> new IllegalArgumentException("Discount không tồn tại"));
        
        entity.setCombo(combo);
        entity.setDiscount(discount);
        entity.setDiscountPercentage(dto.getDiscountPercentage());
        
        return repository.save(entity);
    }

    @Transactional
    public boolean delete(int id) {
        try {
            if (!repository.existsById(id)) {
                return false; 
            }
            repository.deleteById(id);
            return true;
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Không thể xóa do tồn tại ràng buộc dữ liệu. Hãy xóa các bản ghi liên quan trước.");
        }
    }

    
    private void validateDto(DiscountCombodtos dto) {
        if (dto.getComboId() == null || dto.getDiscountId() == null || dto.getDiscountPercentage() == null) {
            throw new IllegalArgumentException("Thiếu thông tin bắt buộc");
        }
        if (dto.getDiscountPercentage().compareTo(BigDecimal.ZERO) < 0 || 
            dto.getDiscountPercentage().compareTo(new BigDecimal(100)) > 0) {
            throw new IllegalArgumentException("Phần trăm giảm giá phải từ 0 đến 100");
        }
    }

    private DiscountCombodtos convertToDto(DiscountComboEntity entity) {
        return new DiscountCombodtos(
        		entity.getId(),
                entity.getCombo().getId(),
                entity.getDiscount().getId(),
                entity.getDiscountPercentage()
        );
    }
}