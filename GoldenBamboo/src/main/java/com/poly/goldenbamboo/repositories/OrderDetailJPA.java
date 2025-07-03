package com.poly.goldenbamboo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.goldenbamboo.dtos.OrderDetailDTO;
import com.poly.goldenbamboo.entities.OrderDetailEntity;

public interface OrderDetailJPA extends JpaRepository<OrderDetailEntity, Integer> {
	List<OrderDetailEntity> findByOrderId(Integer orderId);

	@Query("SELECT od FROM OrderDetailEntity od WHERE od.order.id = :orderId AND od.dishOrComboId = :dishOrComboId AND od.type = :type")
	Optional<OrderDetailEntity> findByOrderIdAndDishOrComboIdAndType(
	    @Param("orderId") Integer orderId, 
	    @Param("dishOrComboId") Integer dishOrComboId,
	    @Param("type") boolean type
	);
	
//	OrderDetailDTO updateQuantity(Integer orderDetailId, Integer quantity);
//    
//    void removeOrderDetailById(Integer detailId);

}
