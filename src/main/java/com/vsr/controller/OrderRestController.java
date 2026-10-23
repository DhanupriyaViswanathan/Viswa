package com.vsr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vsr.composite.Order;
import com.vsr.composite.User;
import com.vsr.service.OrderService;
import com.vsr.service.UserService;

@Controller
@RequestMapping("/api/order")
public class OrderRestController{
	private OrderService orderService;
	private UserService userService;
	
	public OrderRestController(UserService userService,OrderService orderService) {
		this.userService = userService;
		this.orderService = orderService;
	}

	@GetMapping("/{email}")
    public User getUser(@PathVariable String email) {
        return userService.findByEmail(email);
    }
	

	@PostMapping
    public Order create(@RequestBody Order order) {
        return orderService.save(order);
    }
}
