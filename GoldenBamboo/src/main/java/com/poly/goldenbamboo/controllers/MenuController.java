package com.poly.goldenbamboo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.poly.goldenbamboo.entities.MenuEntity;
import com.poly.goldenbamboo.services.MenuService;

import java.util.List;

@RestController
@RequestMapping("/Menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    // Lấy tất cả menu
    @GetMapping
    public List<MenuEntity> getAllMenus() {
        return menuService.getAllMenu();
    }

    // Lấy menu theo ID
    @GetMapping("/{id}")
    public ResponseEntity<MenuEntity> getMenuById(@PathVariable Integer id) {
        MenuEntity menu = menuService.getMenuById(id);
        return menu != null ? ResponseEntity.ok(menu) : ResponseEntity.notFound().build();
    }

    // Tạo mới menu
    @PostMapping
    public MenuEntity createMenu(@RequestBody MenuEntity menu) {
        return menuService.createMenu(menu);
    }

    // Cập nhật menu
    @PutMapping("/{id}")
    public ResponseEntity<MenuEntity> updateMenu(@PathVariable Integer id, @RequestBody MenuEntity updatedMenu) {
        updatedMenu.setId(id);
        MenuEntity menu = menuService.updateMenu(updatedMenu);
        return menu != null ? ResponseEntity.ok(menu) : ResponseEntity.notFound().build();
    }

    // Xóa menu
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Integer id) {
        return menuService.deleteMenu(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    


//    @GetMapping("/{id}/items")
//    public ResponseEntity<List<MenuItemEntity>> getMenuItemsByMenuId(@PathVariable("id") Integer id) {
//        List<MenuItemEntity> menuItems = menuService.getMenuItemsByMenuId(id);
//        if (menuItems.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(menuItems);
//    }

}
