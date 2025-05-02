package com.poly.goldenbamboo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.goldenbamboo.entities.OrderEntity;
import com.poly.goldenbamboo.services.OrderService;

@RestController
@RequestMapping("/Order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
//	@GetMapping
//	public List<OrderEntity> getOrder(){
//		return orderService.getAllOrders();
//	}

}
