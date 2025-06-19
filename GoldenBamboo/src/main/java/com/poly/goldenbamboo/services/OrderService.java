package com.poly.goldenbamboo.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.goldenbamboo.dtos.OrderDTO;
import com.poly.goldenbamboo.dtos.OrderDetailDTO;
import com.poly.goldenbamboo.entities.OrderDetailEntity;
import com.poly.goldenbamboo.entities.OrderEntity;
import com.poly.goldenbamboo.mappers.OrderMapper;
import com.poly.goldenbamboo.repositories.ComboJPA;
import com.poly.goldenbamboo.repositories.DishJPA;
import com.poly.goldenbamboo.repositories.OrderDetailJPA;
import com.poly.goldenbamboo.repositories.OrderJPA;

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
	
    OrderService(DishJPA dishJPA) {
        this.dishJPA = dishJPA;
    }

    public List<OrderDTO> getAllOrdersDTO() {
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
    
    //Kiểm tra món đã tồn tại trong đơn hàng chưa
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
        Optional<OrderDetailEntity> optionalDetail =
                orderDetailJPA.findByOrderIdAndDishOrComboIdAndType(
                        dto.getOrderId(),
                        dto.getDishOrComboId(),
                        dto.isType()
                );

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
            newDetail.setOrder(getOrderEntityById(dto.getOrderId()));
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

    

}

