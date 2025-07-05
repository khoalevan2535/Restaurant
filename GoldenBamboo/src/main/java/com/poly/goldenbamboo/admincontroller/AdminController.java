package com.poly.goldenbamboo.admincontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.goldenbamboo.dtos.BranchDTO;
import com.poly.goldenbamboo.dtos.OrderDTO;
import com.poly.goldenbamboo.dtos.OrderDetailDTO;
import com.poly.goldenbamboo.entities.CategoryEntity;
import com.poly.goldenbamboo.entities.ComboEntity;
import com.poly.goldenbamboo.entities.DishEntity;
import com.poly.goldenbamboo.entities.OrderDetailEntity;
import com.poly.goldenbamboo.entities.TableEntity;
import com.poly.goldenbamboo.services.AccountService;
import com.poly.goldenbamboo.services.CategoryService;
//import com.poly.goldenbamboo.services.ComboService;
import com.poly.goldenbamboo.services.DishService;
import com.poly.goldenbamboo.services.OrderDetailService;
import com.poly.goldenbamboo.services.OrderService;
import com.poly.goldenbamboo.services.TableService;

@RestController
public class AdminController {

	@Autowired
	private TableService tableService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private OrderDetailService orderDetailService;

//	@Autowired
//	private ComboService comboService;

	@Autowired
	private DishService dishService;

	@Autowired
	private AccountService accountService;

	// Lấy danh sách bàn theo chi nhánh


	

	// Thực hiện chọn bàn và tạo hóa đơn
	@PostMapping("/Staff/Branch/{branchId}/Table/{tableId}")
	public OrderDTO createOrderDTO(@PathVariable("branchId") Integer branchId, @PathVariable("tableId") Integer tableId,
			@CookieValue("userId") Integer userId, @RequestBody(required = false) OrderDTO dto) {

		if (dto == null) {
			dto = new OrderDTO();
		}

		dto.setAccountId(userId);
		dto.setBranchId(branchId);
		dto.setTableId(tableId);
		return orderService.createOrder(dto);
	}


}
