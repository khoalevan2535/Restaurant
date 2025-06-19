package com.poly.goldenbamboo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.goldenbamboo.dtos.BranchDTO;
import com.poly.goldenbamboo.entities.BranchEntity;
import com.poly.goldenbamboo.entities.DishEntity;
import com.poly.goldenbamboo.entities.MenuEntity;
import com.poly.goldenbamboo.entities.TableEntity;
import com.poly.goldenbamboo.mappers.BranchMapper;
import com.poly.goldenbamboo.services.BranchService;
import com.poly.goldenbamboo.services.DishService;
import com.poly.goldenbamboo.services.MenuService;
import com.poly.goldenbamboo.services.TableService;

@RestController
public class BranchController {

    @Autowired
    private BranchService branchService;

    @Autowired
    private TableService tableService;

    @GetMapping("/Branch")
    public ResponseEntity<List<BranchDTO>> getAllBranches() {
        return ResponseEntity.ok(branchService.getAllBranch());
    }
    

    @GetMapping("/Branch/Active")
    public List<BranchEntity> getActiveBranches() {
        return branchService.getActiveBranches();
    }

    @GetMapping("/Branch/{branchId}")
    public ResponseEntity<BranchDTO> getBranchById(@PathVariable("branchId") Integer id) {
        BranchDTO dto = branchService.getBranchById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    // Thêm chi nhánh (dùng DTO vào, DTO ra)
    @PostMapping("/Manager/Branch/Add")
    public ResponseEntity<BranchDTO> addBranch(@RequestBody BranchDTO dto) {
        BranchDTO newBranch = branchService.createBranch(dto);
        return ResponseEntity.ok(newBranch);
    }

    // Cập nhật chi nhánh (dùng DTO vào, DTO ra)
    @PutMapping("/Manager/Branch/Update/{id}")
    public ResponseEntity<BranchDTO> updateBranch(@PathVariable("id") Integer id, @RequestBody BranchDTO dto) {
        BranchDTO updated = branchService.updateBranch(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // Xóa chi nhánh
    @DeleteMapping("/Manager/Branch/Delete/{id}")
    public ResponseEntity<String> deleteBranch(@PathVariable("id") Integer id) {
        boolean isDeleted = branchService.deleteBranchById(id);
        return isDeleted
            ? ResponseEntity.ok("Xóa chi nhánh thành công!")
            : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy chi nhánh!");
    }
}
