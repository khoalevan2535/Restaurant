package com.poly.goldenbamboo.services;


import com.poly.goldenbamboo.repositories.ComboDishJPA;
import com.poly.goldenbamboo.repositories.ComboJPA;
import com.poly.goldenbamboo.repositories.DishJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ComboDishService {

    @Autowired
    private ComboDishJPA comboDishJPA;

    @Autowired
    private ComboJPA comboJPA;

    @Autowired
    private DishJPA dishJPA;

//    @Transactional
//    public ComboDishFullDTO createComboDish(int comboId, int dish1Id, int dish2Id) {
//        ComboEntity combo = comboJPA.findById(comboId)
//                .orElseThrow(() -> new RuntimeException("Combo not found"));
//
//        DishEntity dish1 = dishJPA.findById(dish1Id)
//                .orElseThrow(() -> new RuntimeException("Dish 1 not found"));
//
//        DishEntity dish2 = dishJPA.findById(dish2Id)
//                .orElseThrow(() -> new RuntimeException("Dish 2 not found"));
//
//        long existingCount = comboDishJPA.countByComboId(comboId);
//        if (existingCount >= 2) {
//            throw new RuntimeException("Combo already has 2 dishes");
//        }
//
//
//        ComboDishEntity comboDish1 = new ComboDishEntity();
//        comboDish1.setCombo(combo);
//        comboDish1.setDishe(dish1);
//        comboDishJPA.save(comboDish1);
//
//        ComboDishEntity comboDish2 = new ComboDishEntity();
//        comboDish2.setCombo(combo);
//        comboDish2.setDishe(dish2);
//        comboDishJPA.save(comboDish2);
//
//        return new ComboDishFullDTO(
//                comboDish1.getId(),
//                comboId,
//                combo.getName(),
//                dish1Id,
//                dish1.getName(),
//                dish2Id,
//                dish2.getName()
//        );
//    }

//    @Transactional
//    public ComboDishFullDTO updateComboDish(int comboDishId, int comboId, int dish1Id, int dish2Id) {
//        comboDishJPA.deleteByComboId(comboId);
//        return createComboDish(comboId, dish1Id, dish2Id);
//    }
//
//    @Transactional
//    public void deleteComboDish(int id) {
//        comboDishJPA.deleteById(id);
//    }
//
//    @Transactional
//    public void deleteComboDishByDishId(int comboDishId) {
//        ComboDishEntity entity = comboDishJPA.findById(comboDishId)
//            .orElseThrow(() -> new RuntimeException("ComboDish not found"));
//
//        int comboId = entity.getCombo().getId();
//        comboDishJPA.deleteByComboId(comboId);
//    }


//    public List<ComboDishFullDTO> getComboDishFullDTOs() {
//        List<ComboDishEntity> comboDishes = comboDishJPA.findAll();
//
//        return comboDishes.stream()
//                .collect(Collectors.groupingBy(cd -> cd.getCombo().getId()))
//                .entrySet().stream()
//                .filter(e -> e.getValue().size() == 2)
//                .map(entry -> {
//                    ComboDishEntity dish1 = entry.getValue().get(0);
//                    ComboDishEntity dish2 = entry.getValue().get(1);
//                    return new ComboDishFullDTO(
//                            dish1.getId(),
//                            dish1.getCombo().getId(),
//                            dish1.getCombo().getName(),
//                            dish1.getDishe().getId(),
//                            dish1.getDishe().getName(),
//                            dish2.getDishe().getId(),
//                            dish2.getDishe().getName()
//                    );
//                })
//                .collect(Collectors.toList());
//    }
}
