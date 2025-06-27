package com.poly.goldenbamboo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.poly.goldenbamboo.dtos.OrderDetailDTO;
import com.poly.goldenbamboo.entities.OrderDetailEntity;
import com.poly.goldenbamboo.entities.OrderEntity;
import com.poly.goldenbamboo.mappers.OrderDetailMapper;
import com.poly.goldenbamboo.repositories.OrderDetailJPA;

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
        OrderEntity order = orderService.getOrderById(dto.getOrderId());

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
	
	public OrderDetailDTO updateDishQuantity(Integer orderId, Integer orderDetailId, Integer newQuantity) {
	    Optional<OrderDetailEntity> orderDetailOpt = orderDetailJPA.findById(orderDetailId);
	    if (orderDetailOpt.isPresent()) {
	        OrderDetailEntity orderDetail = orderDetailOpt.get();

	        // Kiểm tra xem orderId có khớp với Order này không
	        if (orderDetail.getOrder().getId() == orderId) {
	            orderDetail.setQuantity(newQuantity);
	            orderDetail = orderDetailJPA.save(orderDetail); 

	            return orderDetailMapper.toDTO(orderDetail); 
	        }
	    }
	    throw new RuntimeException("Không tìm thấy món ăn với ID: " + orderDetailId);
	}
	
	public OrderDetailEntity addDishToOrder(OrderDetailDTO dto) {
        // Lấy order theo orderId
        OrderEntity order = orderService.getOrderById(dto.getOrderId());

        Optional<OrderDetailEntity> existingDetailOpt = orderDetailJPA
            .findByOrderIdAndDishOrComboIdAndType(dto.getOrderId(), dto.getDishOrComboId(), dto.isType());

        OrderDetailEntity orderDetail;
        if (existingDetailOpt.isPresent()) {
            orderDetail = existingDetailOpt.get();
            orderDetail.setQuantity(orderDetail.getQuantity() + dto.getQuantity());
            // Cập nhật giá nếu cần
            orderDetail.setPrice(dto.getPrice());
            orderDetail.setDiscountPercentage(dto.getDiscountPercentage());
        } else {
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



}
