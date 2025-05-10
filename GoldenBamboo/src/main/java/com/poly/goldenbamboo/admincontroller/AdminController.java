package com.poly.goldenbamboo.admincontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.goldenbamboo.dtos.OrderDTO;
import com.poly.goldenbamboo.dtos.TableDTO;
import com.poly.goldenbamboo.services.OrderService;
import com.poly.goldenbamboo.services.TableService;

@RestController
@RequestMapping("/Admin")
public class AdminController {

	@Autowired
	private TableService tableService;

	@Autowired
	private OrderService orderService;

	// Lấy danh sách bàn theo chi nhánh
	@GetMapping("/Branch/{branchId}/Tables")
	public ResponseEntity<List<TableDTO>> getTablesByBranch(@PathVariable("branchId") Integer branchId) {
		return ResponseEntity.ok(tableService.getAllTableByBranchId(branchId));
	}

	// Thực hiện chọn bàn và tạo hóa đơn
	@PostMapping("/Branch/{branchId}/Table/{tableId}")
	public OrderDTO createOrderDTO(@PathVariable("branchId") Integer branchId, @PathVariable("tableId") Integer tableId,
			@CookieValue("userId") Integer userId, @RequestBody OrderDTO dto) {

		dto.setAccountId(userId);
		dto.setBranchId(branchId);
		TableDTO tableDTO = new TableDTO();
	    tableDTO.setId(tableId);
	    dto.setTableId(tableDTO.getId());
	    
		return orderService.createOrder(dto);
	}

	@GetMapping("/Branch/{branchId}/Table/{tableId}/Order/{orderId}")
	public Map<String, Object> Order(@PathVariable("branchId") Integer branchId) {
		Map<String, Object> res = new HashMap<String, Object>();

		List<TableDTO> tables = tableService.getAllTableByBranchId(branchId);

		res.put("tables", tables);

		return res;
	}
}
