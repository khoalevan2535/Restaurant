package com.poly.goldenbamboo.admincontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.goldenbamboo.dtos.TableDTO;
import com.poly.goldenbamboo.services.TableService;

@RestController
@RequestMapping("/Admin")
public class AdminController {
	
	@Autowired
	private TableService tableService;
	
	// Lấy danh sách bàn theo chi nhánh
	@GetMapping("/Branch/{branchId}/Tables")
	public ResponseEntity<List<TableDTO>> getTablesByBranch(@PathVariable("branchId") Integer branchId) {
        System.out.println(branchId);
		return ResponseEntity.ok(tableService.getAllTableByBranchId(branchId));
    }
}
