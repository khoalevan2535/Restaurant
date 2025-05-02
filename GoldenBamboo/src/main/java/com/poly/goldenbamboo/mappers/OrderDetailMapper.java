package com.poly.goldenbamboo.mappers;

import org.springframework.stereotype.Component;

import com.poly.goldenbamboo.dtos.OrderDetailDTO;
import com.poly.goldenbamboo.entities.OrderDetailEntity;
import com.poly.goldenbamboo.entities.OrderEntity;

@Component
public class OrderDetailMapper {
	public OrderDetailEntity toEntity(OrderDetailDTO dto) {
	    OrderDetailEntity entity = new OrderDetailEntity();

	    // Khởi tạo OrderEntity với id
	    OrderEntity order = new OrderEntity();
	    order.setId(dto.getOrderId());

	    entity.setId(dto.getId());
	    entity.setOrder(order);
	    entity.setDishOrComboId(dto.getDishOrComboId());
	    entity.setPrice(dto.getPrice());
	    entity.setQuantity(dto.getQuantity());
	    entity.setType(dto.isType());
	    entity.setDiscountPercentage(dto.getDiscountPercentage());

	    return entity;
	}
	
	public OrderDetailDTO toDTO(OrderDetailEntity entity) {
		OrderDetailDTO dto = new OrderDetailDTO();
		dto.setId(entity.getId());
		dto.setOrderId(entity.getOrder().getId());
		dto.setDishOrComboId(entity.getDishOrComboId());
		dto.setPrice(entity.getPrice());
		dto.setQuantity(entity.getQuantity());
		dto.setType(entity.isType());
		dto.setDiscountPercentage(entity.getDiscountPercentage());
		return dto;
	}
}
