package com.poly.goldenbamboo.repositories;

import com.poly.goldenbamboo.entities.ComboDishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComboDishJPA extends JpaRepository<ComboDishEntity, Integer> {
    List<ComboDishEntity> findByComboId(int comboId);
    long countByComboId(int comboId);
    void deleteByComboId(int comboId);
}