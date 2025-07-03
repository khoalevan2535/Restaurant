package com.poly.goldenbamboo.services;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.goldenbamboo.dtos.OrderDTO;
import com.poly.goldenbamboo.dtos.OrderDetailDTO;
import com.poly.goldenbamboo.entities.AccountEntity;
import com.poly.goldenbamboo.entities.OrderDetailEntity;
import com.poly.goldenbamboo.entities.OrderEntity;
import com.poly.goldenbamboo.entities.TableEntity;
import com.poly.goldenbamboo.mappers.OrderMapper;
import com.poly.goldenbamboo.repositories.AccountJPA;
import com.poly.goldenbamboo.repositories.ComboJPA;
import com.poly.goldenbamboo.repositories.DishJPA;
import com.poly.goldenbamboo.repositories.OrderDetailJPA;
import com.poly.goldenbamboo.repositories.OrderJPA;
import com.poly.goldenbamboo.repositories.TableJPA;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

	@Autowired
	private DishJPA dishJPA;

	@Autowired
	private OrderJPA orderRepository;

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrderDetailJPA orderDetailJPA;

	@Autowired
	private ComboJPA comboJPA;
	
	@Autowired
	private TableJPA tableJPA;
	
	@Autowired
	private AccountJPA accountJPA;
	

	OrderService(DishJPA dishJPA) {
		this.dishJPA = dishJPA;
	}

	public List<OrderDTO> getAllOrdersDTO() {
		return orderRepository.findAll().stream().map(orderMapper::toDTO).collect(Collectors.toList());
	}
	
	public OrderEntity getOrderEntityById(Integer orderId) {
	    return orderRepository.findById(orderId)
	            .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với ID: " + orderId));
	}
	
	public OrderDTO getOrderById(Integer orderId) {
		// 1. Truy vấn OrderEntity từ Repository
		OrderEntity orderEntity = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với ID: " + orderId));

		// 2. Sử dụng OrderMapper để chuyển đổi OrderEntity sang OrderDTO
		return orderMapper.toDTO(orderEntity);
	}

	// Thêm mới đơn hàng
	public OrderDTO createOrder(OrderDTO dto) {
		OrderEntity entity = orderMapper.toEntity(dto);
		OrderEntity saved = orderRepository.save(entity);
		OrderDTO result = orderMapper.toDTO(saved);

		return result;
	}

	// Cập nhật đơn hàng
	public OrderDTO updateOrder(OrderDTO dto) {
		OrderEntity entity = orderMapper.toEntity(dto);
		OrderEntity updated = orderRepository.save(entity);
		return orderMapper.toDTO(updated);
	}

	// Xóa đơn hàng
	public void deleteOrder(int id) {
		orderRepository.deleteById(id);
	}

	public void updateQuantity(int orderId, int orderDetailId, int quantity) {
		Optional<OrderDetailEntity> optional = orderDetailJPA.findById(orderDetailId);
		if (optional.isPresent()) {
			OrderDetailEntity detail = optional.get();

			if (detail.getOrder().getId() != orderId) {
				throw new RuntimeException("❌ Order ID không khớp!");
			}

			detail.setQuantity(quantity);
			orderDetailJPA.save(detail);
		} else {
			throw new RuntimeException("❌ Không tìm thấy orderDetailId: " + orderDetailId);
		}
	}

	public void updateOrderTotal(int orderId) {
		OrderEntity order = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với ID: " + orderId));

		BigDecimal totalAmount = calculateTotalAmount(orderId);
		order.setTotalAmount(totalAmount);
		orderRepository.save(order);
	}

	public BigDecimal calculateTotalAmount(int orderId) {
		List<OrderDetailEntity> details = orderDetailJPA.findByOrderId(orderId);
		BigDecimal total = BigDecimal.ZERO;

		for (OrderDetailEntity detail : details) {
			BigDecimal lineTotal = detail.getPrice().multiply(BigDecimal.valueOf(detail.getQuantity()));
			total = total.add(lineTotal);
		}

		return total;
	}

	// Kiểm tra món đã tồn tại trong đơn hàng chưa
	public boolean isDishOrComboExist(int id, boolean isDish) {
		if (isDish) {
			return dishJPA.existsById(id);
		} else {
			return comboJPA.existsById(id);
		}
	}

	public String addOrUpdateOrderDetail(OrderDetailDTO dto) {
		// Kiểm tra Dish/Combo có tồn tại không
		boolean exists = isDishOrComboExist(dto.getDishOrComboId(), dto.isType());
		if (!exists) {
			return "❌ Món ăn hoặc Combo không tồn tại!";
		}

		// Kiểm tra món đã có trong đơn hàng chưa
		Optional<OrderDetailEntity> optionalDetail = orderDetailJPA
				.findByOrderIdAndDishOrComboIdAndType(dto.getOrderId(), dto.getDishOrComboId(), dto.isType());

		if (optionalDetail.isPresent()) {
			// Nếu đã tồn tại → cập nhật
			OrderDetailEntity existing = optionalDetail.get();
			existing.setQuantity(existing.getQuantity() + dto.getQuantity());
			existing.setPrice(dto.getPrice());
			existing.setDiscountPercentage(dto.getDiscountPercentage());

			orderDetailJPA.save(existing);
		} else {
			// Nếu chưa tồn tại → thêm mới
			OrderDetailEntity newDetail = new OrderDetailEntity();

			newDetail.setDishOrComboId(dto.getDishOrComboId());
			newDetail.setType(dto.isType());
			newDetail.setPrice(dto.getPrice());
			newDetail.setQuantity(dto.getQuantity());
			newDetail.setDiscountPercentage(dto.getDiscountPercentage());

			orderDetailJPA.save(newDetail);
		}

		// Cập nhật lại tổng tiền đơn hàng
		updateOrderTotal(dto.getOrderId());

		return "✅ Xử lý món thành công.";
	}
	 public OrderEntity findOrderById(Integer orderId) {
	        return orderRepository.findById(orderId)
	                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng với ID: " + orderId));
	    }
	 
	public OrderEntity checkoutOrder(Integer orderId) {
        // BƯỚC 1: Tìm đơn hàng trong database bằng ID
        OrderEntity order = findOrderById(orderId);

        // BƯỚC 2: Tính lại tổng tiền một cách an toàn từ dữ liệu của server
        BigDecimal finalAmount = calculateTotalAmount(orderId);
        
        // BƯỚC 3: Cập nhật trạng thái và tổng tiền cuối cùng cho đơn hàng
        order.setTotalAmount(finalAmount);
        order.setStatus(1); // Đặt trạng thái là "Đã thanh toán"
        
        // BƯỚC 4: Lưu lại đơn hàng đã cập nhật vào DB và trả về kết quả
        return orderRepository.save(order);
    }

	// Trong file OrderService.java

	// Trong file OrderService.java

	// Trong file OrderService.java

	@Transactional
	public OrderEntity findOrCreateOrderForTable(Integer tableId, Integer accountId) {
	    final int PAID_STATUS = 2; // Giả sử 2 là "Đã thanh toán"
	    
	    // 1. Tìm TẤT CẢ đơn hàng chưa thanh toán cho bàn này, sắp xếp theo ngày mới nhất
	    List<OrderEntity> existingOrders = orderRepository.findByTableIdAndStatusNotOrderByOrderDateDesc(tableId, PAID_STATUS);

	    // 2. Kiểm tra danh sách
	    if (!existingOrders.isEmpty()) {
	        // Nếu có, luôn lấy đơn hàng ĐẦU TIÊN (là đơn hàng mới nhất)
	        return existingOrders.get(0); 
	    } else {
	        // 3. Nếu không có, tạo một đơn hàng mới (logic này không đổi)
	        OrderEntity newOrder = new OrderEntity();
	        TableEntity table = tableJPA.findById(tableId)
	            .orElseThrow(() -> new RuntimeException("Không tìm thấy bàn với ID: " + tableId));
	        AccountEntity account = accountJPA.findById(accountId)
	            .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản với ID: " + accountId));

	        newOrder.setAccount(account);
	        newOrder.setTable(table);
	        if (table.getBranch() != null) {
	            newOrder.setBranch(table.getBranch());
	        }
	        newOrder.setStatus(0); // PENDING
	        newOrder.setTotalAmount(BigDecimal.ZERO);
	        newOrder.setPrepay(BigDecimal.ZERO); 
	        newOrder.setPaymentMethod("Tại quầy"); 
	        newOrder.setDescription(""); 

	        table.setStatus(1); // Có khách
	        tableJPA.save(table);

	        return orderRepository.save(newOrder);
	    }
	}
	// Trong file OrderService.java

	@Transactional
	public OrderEntity forceCreateNewOrderForTable(Integer tableId, Integer accountId) {
	    // Logic này chỉ tạo mới, không tìm kiếm
	    OrderEntity newOrder = new OrderEntity();
	    
	    TableEntity table = tableJPA.findById(tableId)
	        .orElseThrow(() -> new RuntimeException("Không tìm thấy bàn với ID: " + tableId));
	    
	    AccountEntity account = accountJPA.findById(accountId)
	        .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản với ID: " + accountId));

	    newOrder.setAccount(account);
	    newOrder.setTable(table);
	    
	    if (table.getBranch() != null) {
	        newOrder.setBranch(table.getBranch());
	    } else {
	        throw new RuntimeException("Không thể xác định chi nhánh cho đơn hàng.");
	    }
	    
	    // Thiết lập giá trị mặc định
	    newOrder.setStatus(0); // PENDING
	    newOrder.setTotalAmount(BigDecimal.ZERO);
	    newOrder.setPrepay(BigDecimal.ZERO); 
	    newOrder.setPaymentMethod("Tại quầy"); 
	    newOrder.setDescription("Tách đơn hoặc đơn mới"); 

	    // Không cần thay đổi trạng thái bàn vì bàn đã có khách rồi

	    return orderRepository.save(newOrder);
	}
}
