package com.poly.goldenbamboo.staffcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.poly.goldenbamboo.controllers.ReservationController;
import com.poly.goldenbamboo.dtos.BranchDTO;
import com.poly.goldenbamboo.dtos.OrderDTO;
import com.poly.goldenbamboo.dtos.OrderDetailDTO;
import com.poly.goldenbamboo.entities.CategoryEntity;
import com.poly.goldenbamboo.entities.ComboEntity;
import com.poly.goldenbamboo.entities.DishEntity;
import com.poly.goldenbamboo.entities.OrderDetailEntity;
import com.poly.goldenbamboo.services.AccountService;
import com.poly.goldenbamboo.services.BranchService;
import com.poly.goldenbamboo.services.CategoryService;
import com.poly.goldenbamboo.services.ComboService;
import com.poly.goldenbamboo.services.DishService;
import com.poly.goldenbamboo.services.OrderDetailService;
import com.poly.goldenbamboo.services.OrderService;
import com.poly.goldenbamboo.services.TableService;

import jakarta.persistence.EntityNotFoundException;

@RestController
//@RequestMapping("/Staff")
public class HomeStaffController {

	private final ReservationController reservationController;

	@Autowired
	private OrderService orderService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private DishService dishService;

	@Autowired
	private TableService tableService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private BranchService branchService;
	
	@Autowired
	private ComboService comboService;

	HomeStaffController(ReservationController reservationController) {
		this.reservationController = reservationController;
	}

	@GetMapping("/Staff")
	public BranchDTO findBranchByUserIdCookie(@CookieValue("userId") Integer userId) {
		return accountService.getBranchByUserId(userId);
	}

	@GetMapping("/Staff/Branch/{branchId}")
	public BranchDTO getBranchById(@PathVariable("branchId") Integer branchId) {
		BranchDTO branch = branchService.getBranchById(branchId);

		return branch;
	}
	

	@GetMapping("/Staff/Branch/{branchId}/Order/{orderId}/Category/{categoryId}")
	public Map<String, Object> orderDetailMap(
	        @PathVariable("branchId") Integer branchId,
	        @PathVariable("orderId") Integer orderId,
	        @PathVariable("categoryId") Integer categoryId) {

	    Map<String, Object> response = new HashMap<>();

	    BranchDTO branch = branchService.getBranchById(branchId);
	    List<CategoryEntity> categories = categoryService.getAllCategory();
	    
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

	    List<ComboEntity> combos = comboService.getDefaultCombosByBranch(branchId);

	    List<DishEntity> foods = new ArrayList<>();
	    if (categoryId != -1) {
	        foods = dishService.getDefaultMenuDishesByBranchAndCategory(branchId, categoryId);
	    }
	    
	    response.put("orderDetails", orderDetails);
	    response.put("branch", branch);
	    response.put("categories", categories);
	    response.put("foods", foods);
	    response.put("combos", combos);    

	    return response;
	}


	@PostMapping("/Staff")
	public OrderDTO createOrder(@RequestBody OrderDTO dto, @CookieValue("userId") Integer userId) {
		dto.setAccountId(userId);
		return orderService.createOrder(dto);
	}

	@PostMapping("/AddFoodToOrder")
	public OrderDetailEntity addDishToOrder(@RequestBody OrderDetailDTO dto) {
		return orderDetailService.addDishToOrder(dto); 
	}
	
	@DeleteMapping("/Staff/Branch/{branchId}/Order/{orderId}/OrderDetail/{orderDetailId}")
	public ResponseEntity<String> removeDishFromOrder(@PathVariable("branchId") Integer branchId,
	                                                  @PathVariable("orderId") Integer orderId,
	                                                  @PathVariable("orderDetailId") Integer orderDetailId) {
	    try {
	        boolean isRemoved = orderDetailService.removeDishFromOrder(orderId, orderDetailId);
	        if (isRemoved) {
	            return ResponseEntity.status(HttpStatus.OK).body("Món đã được xóa khỏi giỏ hàng.");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Món không tồn tại trong giỏ hàng.");
	        }
	    } catch (EntityNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy món hoặc đơn hàng.");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi xóa món khỏi giỏ hàng.");
	    }
	}
	
	@PutMapping("/Staff/Order/{orderId}/OrderDetail/{orderDetailId}/updateQuantity")
	public ResponseEntity<?> updateDishQuantity(
	    @PathVariable("orderId") int orderId,
	    @PathVariable("orderDetailId") int orderDetailId,
	    @RequestBody Map<String, Integer> body
	) {
	    int quantity = body.get("quantity");
	    // Gọi service để update vào DB
	    orderService.updateQuantity(orderId, orderDetailId, quantity);
	    return ResponseEntity.ok().build();
	}


}
