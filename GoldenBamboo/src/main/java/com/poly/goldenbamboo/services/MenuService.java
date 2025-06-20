package com.poly.goldenbamboo.services;

import com.poly.goldenbamboo.entities.MenuEntity;
import com.poly.goldenbamboo.repositories.MenuJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuJPA menuJPA;  

    
    //Lấy tất cả menu theo branchId
    public List<MenuEntity> getMenuByBranchId(Integer branchId) {
    	return menuJPA.findByBranchId(branchId);
    }

    // Lấy menu theo ID
    public MenuEntity getMenuById(Integer id) {
        return menuJPA.findById(id).orElse(null);
    }

    // Tạo menu mới
    public MenuEntity createMenu(MenuEntity menu) {
        return menuJPA.save(menu);
    }

    // Cập nhật menu
    public MenuEntity updateMenu(MenuEntity updatedMenu) {
        return menuJPA.save(updatedMenu);
    }

    // Xóa menu
    public boolean deleteMenu(Integer id) {
        if (menuJPA.existsById(id)) {
            menuJPA.deleteById(id);
            return true;
        }
        return false;
    }
}
