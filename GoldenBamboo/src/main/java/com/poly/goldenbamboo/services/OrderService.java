package com.poly.goldenbamboo.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.goldenbamboo.dtos.OrderDTO;
import com.poly.goldenbamboo.entities.OrderDetailEntity;
import com.poly.goldenbamboo.entities.OrderEntity;
import com.poly.goldenbamboo.mappers.OrderMapper;
import com.poly.goldenbamboo.repositories.OrderDetailJPA;
import com.poly.goldenbamboo.repositories.OrderJPA;

@Service
public class OrderService {

	@Autowired
	private OrderJPA orderRepository;

	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrderDetailJPA orderDetailJPA;

	// Lấy tất cả đơn hàng
	public List<OrderDTO> getAllOrders() {
		return orderRepository.findAll()
		                      .stream()
		                      .map(orderMapper::toDTO)
		                      .collect(Collectors.toList());
	}

    public OrderEntity getOrderEntityById(Integer orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
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
}

