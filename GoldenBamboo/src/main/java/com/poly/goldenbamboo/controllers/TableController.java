package com.poly.goldenbamboo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.goldenbamboo.dtos.TableDTO;
import com.poly.goldenbamboo.entities.TableEntity;
import com.poly.goldenbamboo.services.TableService;

@RestController
@RequestMapping("/api/tables")
public class TableController {

	@Autowired
	private TableService tableService;
	
	@GetMapping("/branch/{branchId}")
    public List<TableDTO> findAllTableByBranch(@PathVariable("branchId") Integer branchId) {
        
        // Controller chỉ gọi đến service để lấy dữ liệu
        // Mọi logic xử lý nằm ở tầng Service
        return tableService.getAllTableByBranchId(branchId);
    }

}
