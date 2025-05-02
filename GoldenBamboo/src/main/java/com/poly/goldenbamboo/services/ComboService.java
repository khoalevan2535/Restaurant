package com.poly.goldenbamboo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.goldenbamboo.GoldenBambooApplication;
import com.poly.goldenbamboo.entities.ComboEntity;
import com.poly.goldenbamboo.repositories.ComboJPA;

@Service
public class ComboService {
    
    @Autowired
    private ComboJPA comboJPA;

    public List<ComboEntity> getAllCombo() {
        return comboJPA.findAll();
    }

    public ComboEntity getComboById(Integer id) {
        Optional<ComboEntity> optional = comboJPA.findById(id);
        return optional.orElse(null);
    }

    public ComboEntity createCombo(ComboEntity comboEntity) {
        comboEntity.setStatus(comboEntity.isStatus()); // Sử dụng isStatus() thay vì getStatus()
        return comboJPA.save(comboEntity);
    }

    public ComboEntity updateCombo(Integer id, ComboEntity updatedCombo) {
        Optional<ComboEntity> optionalCombo = comboJPA.findById(id);
        if (optionalCombo.isPresent()) {
            ComboEntity existingCombo = optionalCombo.get();
            existingCombo.setName(updatedCombo.getName());
            existingCombo.setPrice(updatedCombo.getPrice());
            existingCombo.setDescription(updatedCombo.getDescription());
            existingCombo.setStatus(updatedCombo.isStatus()); // Sử dụng isStatus() thay vì getStatus()
            existingCombo.setImage(updatedCombo.getImage());
            return comboJPA.save(existingCombo);
        } else {
            return null;
        }
    }

    public void deleteCombo(Integer id) {
        comboJPA.deleteById(id);
    }
    
    public List<ComboEntity> getDefaultCombosByBranch(Integer branchId) {
        return comboJPA.findDefaultMenuCombosByBranch(branchId);
    }
}
