package com.poly.goldenbamboo.repositories;

import com.poly.goldenbamboo.entities.Combo_DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComboDishJPA extends JpaRepository<Combo_DishEntity, Integer> {
    List<Combo_DishEntity> findByComboId(int comboId);
    long countByComboId(int comboId);
    void deleteByComboId(int comboId);
}