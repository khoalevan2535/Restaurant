package com.poly.goldenbamboo.admincontroller;

import java.util.ArrayList;
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

import com.poly.goldenbamboo.dtos.BranchDTO;
import com.poly.goldenbamboo.dtos.OrderDTO;
import com.poly.goldenbamboo.dtos.OrderDetailDTO;
import com.poly.goldenbamboo.dtos.TableDTO;
import com.poly.goldenbamboo.entities.CategoryEntity;
import com.poly.goldenbamboo.entities.ComboEntity;
import com.poly.goldenbamboo.entities.DishEntity;
import com.poly.goldenbamboo.entities.TableEntity;
import com.poly.goldenbamboo.services.AccountService;
import com.poly.goldenbamboo.services.CategoryService;
import com.poly.goldenbamboo.services.ComboService;
import com.poly.goldenbamboo.services.DishService;
import com.poly.goldenbamboo.services.OrderDetailService;
import com.poly.goldenbamboo.services.OrderService;
import com.poly.goldenbamboo.services.TableService;

@RestController
@RequestMapping("/Admin")
public class AdminController {

	@Autowired
	private TableService tableService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private ComboService comboService;

	@Autowired
	private DishService dishService;

	@Autowired
	private AccountService accountService;

	// Lấy danh sách bàn theo chi nhánh
	// Lấy chi nhánh dựa vòa cookie
	@GetMapping("/Branch/{branchId}/Tables")
	public Map<String, Object> getTablesByBranch(@PathVariable("branchId") Integer branchId,
			@CookieValue("userId") Integer userId) {

		Map<String, Object> res = new HashMap<>();

		// Lấy chi nhánh từ userId
		BranchDTO userBranch = accountService.getBranchByUserId(userId);

		// So sánh chi nhánh từ user với chi nhánh được yêu cầu
		if (userBranch.getId() != branchId) {
			res.put("status", "error");
			res.put("message", "Bạn không có quyền truy cập chi nhánh này.");
			return res;
		}

		// Trả danh sách bàn nếu hợp lệ
		res.put("status", "success");
		res.put("tables", tableService.getAllTableByBranchId(branchId));
		return res;
	}

	// Thực hiện chọn bàn và tạo hóa đơn
	@PostMapping("/Branch/{branchId}/Table/{tableId}")
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

	// Thực hiện hiển thị giao diện chọn món
	@GetMapping("/Branch/{branchId}/Table/{tableId}/Order/{orderId}/Category/{categoryId}")
	public Map<String, Object> Order(@PathVariable("branchId") Integer branchId,
			@PathVariable("tableId") Integer tableId, @PathVariable("categoryId") Integer categoryId,
			@PathVariable("orderId") Integer orderId) {

		Map<String, Object> res = new HashMap<>();

		// 1. Lấy thông tin bàn
		TableEntity table = tableService.getTableById(tableId);

		// 2. Lấy danh mục
		List<CategoryEntity> categories = categoryService.getAllCategory();

		// 3. Lấy chi tiết đơn hàng
		List<OrderDetailDTO> orderDetails = orderDetailService.getOrderDetailsByOrderId(orderId);
		for (OrderDetailDTO detail : orderDetails) {
			if (detail.isType()) {
				ComboEntity combo = comboService.getComboById(detail.getDishOrComboId());
				if (combo != null) {
					detail.setName(combo.getName());
					detail.setImage(combo.getImage());
					detail.setDescription(combo.getDescription());
				}
			} else {
				DishEntity dish = dishService.getDishById(detail.getDishOrComboId());
				if (dish != null) {
					detail.setName(dish.getName());
					detail.setImage(dish.getImage());
					detail.setDescription(dish.getDescription());
				}
			}
		}

		// 4. Lấy combo mặc định theo chi nhánh
		List<ComboEntity> combos = comboService.getDefaultCombosByBranch(branchId);

		// 5. Lấy danh sách món ăn theo category
		List<DishEntity> foods = new ArrayList<>();
		if (categoryId != -1) {
			foods = dishService.getDefaultMenuDishesByBranchAndCategory(branchId, categoryId);
		}

		// 6. Trả dữ liệu về
		res.put("orderDetails", orderDetails);
		res.put("categories", categories);
		res.put("foods", foods);
		res.put("combos", combos);
		res.put("table", table);

		return res;
	}

}
