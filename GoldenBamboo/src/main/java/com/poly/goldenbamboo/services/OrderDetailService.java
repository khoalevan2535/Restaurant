package com.poly.goldenbamboo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.goldenbamboo.dtos.OrderDetailDTO;
import com.poly.goldenbamboo.entities.OrderDetailEntity;
import com.poly.goldenbamboo.entities.OrderEntity;
import com.poly.goldenbamboo.mappers.OrderDetailMapper;
import com.poly.goldenbamboo.repositories.OrderDetailJPA;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class OrderDetailService {

	@Autowired
	private OrderDetailJPA orderDetailJPA;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderDetailMapper orderDetailMapper;

	public List<OrderDetailEntity> getAllOrderdetail() {
		return orderDetailJPA.findAll();
	}
	
	// Phương thức lấy OrderDetails theo OrderId và chuyển thành DTO
    public List<OrderDetailDTO> getOrderDetailsByOrderId(Integer orderId) {
        List<OrderDetailEntity> orderDetails = orderDetailJPA.findByOrderId(orderId);
        return orderDetails.stream()
                           .map(orderDetailMapper::toDTO)  // Dùng mapper để chuyển thành DTO
                           .collect(Collectors.toList());
    }
    
    public  OrderDetailEntity saveOrUpdateOrderDetail 	(OrderDetailDTO dto) {
    	// Trong OrderDetailService.java
    	OrderEntity order = orderService.getOrderEntityById(dto.getOrderId()); // Gọi phương thức mới

        Optional<OrderDetailEntity> existingDetailOpt = orderDetailJPA
            .findByOrderIdAndDishOrComboIdAndType(dto.getOrderId(), dto.getDishOrComboId(), dto.isType());

        OrderDetailEntity orderDetail;
        if (existingDetailOpt.isPresent()) {
            // Nếu đã có thì tăng số lượng
            orderDetail = existingDetailOpt.get();
            orderDetail.setQuantity(orderDetail.getQuantity() + dto.getQuantity());
        } else {
            // Nếu chưa có thì thêm mới
            orderDetail = new OrderDetailEntity();
            orderDetail.setOrder(order);
            orderDetail.setDishOrComboId(dto.getDishOrComboId());
            orderDetail.setPrice(dto.getPrice());
            orderDetail.setQuantity(dto.getQuantity());
            orderDetail.setType(dto.isType());
            orderDetail.setDiscountPercentage(dto.getDiscountPercentage());
        }

        return orderDetailJPA.save(orderDetail);
    }


	

	public boolean removeDishFromOrder(Integer orderId, Integer orderDetailId) {
		Optional<OrderDetailEntity> orderDetailOpt = orderDetailJPA.findById(orderDetailId);
		if (orderDetailOpt.isPresent()) {
			// Sử dụng toán tử == để so sánh kiểu int với Integer
			if (orderDetailOpt.get().getOrder().getId() == orderId) {
				orderDetailJPA.delete(orderDetailOpt.get());
				return true;
			}
		}
		return false;
	}
	
//	public OrderDetailDTO updateQuantity(Integer orderId, Integer orderDetailId, Integer newQuantity) {
//	    Optional<OrderDetailEntity> orderDetailOpt = orderDetailJPA.findById(orderDetailId);
//	    if (orderDetailOpt.isPresent()) {
//	        OrderDetailEntity orderDetail = orderDetailOpt.get();
//
//	        // Kiểm tra xem orderId có khớp với Order này không
//	        if (orderDetail.getOrder().getId() == orderId) {
//	            orderDetail.setQuantity(newQuantity);
//	            orderDetail = orderDetailJPA.save(orderDetail); 
//
//	            return orderDetailMapper.toDTO(orderDetail); 
//	        }
//	    }
//	    throw new RuntimeException("Không tìm thấy món ăn với ID: " + orderDetailId);
//	}
	public OrderDetailDTO updateQuantity(Integer orderId, Integer orderDetailId, Integer newQuantity) {
        if (newQuantity <= 0) {
            removeDishFromOrder(orderId, orderDetailId);
            return null;
        }

        OrderDetailEntity orderDetail = orderDetailJPA.findById(orderDetailId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy chi tiết đơn hàng với ID: " + orderDetailId));

        if (orderDetail.getOrder().getId() != orderId) {
            throw new SecurityException("Bạn không có quyền sửa chi tiết đơn hàng này.");
        }

        orderDetail.setQuantity(newQuantity);
        OrderDetailEntity savedEntity = orderDetailJPA.save(orderDetail);

        return orderDetailMapper.toDTO(savedEntity);
    }
	
	public OrderDetailEntity addDishToOrder(OrderDetailDTO dto) {
	    // Lấy OrderEntity đầy đủ từ database
		// Trong OrderDetailService.java
		OrderEntity order = orderService.getOrderEntityById(dto.getOrderId()); 
	    if (order == null) {
	        // Xử lý trường hợp không tìm thấy Order (ví dụ: ném ngoại lệ)
	        throw new RuntimeException("Order not found with ID: " + dto.getOrderId());
	    }

	    Optional<OrderDetailEntity> existingDetailOpt = orderDetailJPA
	        .findByOrderIdAndDishOrComboIdAndType(dto.getOrderId(), dto.getDishOrComboId(), dto.isType());

	    OrderDetailEntity orderDetail;
	    if (existingDetailOpt.isPresent()) {
	        orderDetail = existingDetailOpt.get();
	        orderDetail.setQuantity(orderDetail.getQuantity() + dto.getQuantity());
	        orderDetail.setPrice(dto.getPrice());
	        orderDetail.setDiscountPercentage(dto.getDiscountPercentage());
	    } else {
	        // Sử dụng mapper để chuyển đổi DTO sang Entity ban đầu
	        orderDetail = orderDetailMapper.toEntity(dto);
	        // Sau đó, gán OrderEntity đã lấy từ database vào OrderDetailEntity
	        orderDetail.setOrder(order);
	    }

	    return orderDetailJPA.save(orderDetail);
	}
	
	


}
