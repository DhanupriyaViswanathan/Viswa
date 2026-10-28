package com.vsr.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vsr.entity.Order;
import com.vsr.entity.Payment;
import com.vsr.composite.User;
import com.vsr.service.OrderService;
import com.vsr.service.UserService;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/api/order")
public class OrderRestController {
	private OrderService orderService;
	private UserService userService;
	
	public OrderRestController(UserService userService, OrderService orderService) {
		this.userService = userService;
		this.orderService = orderService;
	}

	@GetMapping("/{email}")
	@ResponseBody
    public User getUser(@PathVariable String email) {
        return userService.findByEmail(email);
    }
	
	// 1. Create Order and initialize a PENDING Payment record linked to user & order
	@PostMapping
	@ResponseBody
    public ResponseEntity<Order> create(@RequestBody Order order) {
        order.setOrderStatus("CREATED");
        order.setShipmentSatus("PENDING");
        Order savedOrder = orderService.saveOrder(order);

        // Create initial PENDING payment row tied to user ID
        Payment payment = new Payment();
        payment.setOrderId(savedOrder.getOrderId());
        payment.setUserId(savedOrder.getUserId()); // Captures User ID cleanly
        payment.setModeOfPayment("UPI");
        payment.setPaymantStatus("PENDING");
        payment.setAmount(savedOrder.getTotalPrice() != null ? savedOrder.getTotalPrice().doubleValue() : 0.0);
        orderService.savePayment(payment);

        return ResponseEntity.ok(savedOrder);
    }

	// 2. Update Payment table status to PAID using orderId after successful gateway response
	@PutMapping("/{orderId}/payment")
	@ResponseBody
    public ResponseEntity<Payment> updatePaymentStatus(@PathVariable Long orderId, @RequestBody Payment paymentDetails) {
        Payment existingPayment = orderService.findPaymentByOrderId(orderId);
        if (existingPayment != null) {
            existingPayment.setPaymantStatus("PAID");
            existingPayment.setPaymentId(paymentDetails.getPaymentId());
            Payment savedPayment = orderService.savePayment(existingPayment);

            // Optionally update main order status to processing
            Order order = orderService.findOrderById(orderId);
            if (order != null) {
                order.setOrderStatus("PROCESSING");
                orderService.saveOrder(order);
            }

            return ResponseEntity.ok(savedPayment);
        }
        return ResponseEntity.notFound().build();
    }
}