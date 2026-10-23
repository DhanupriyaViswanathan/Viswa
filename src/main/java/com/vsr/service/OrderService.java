package com.vsr.service;

import org.springframework.stereotype.Service;

import com.vsr.composite.User;
import com.vsr.composite.Order;
import com.vsr.factory.OrderFactory;
import com.vsr.factory.UserFactory;
import com.vsr.repository.OrderRepository;
import com.vsr.repository.UserRepository;

@Service
public class OrderService {
	
	private UserRepository userRepository;
	private OrderRepository orderRepository;
	
	public OrderService(UserRepository userRepository,OrderRepository orderRepository) {
		this.userRepository = userRepository;
		this.orderRepository = orderRepository;
	}
	
	
	public User save(User user) {
        return UserFactory.toComposite(userRepository.save(UserFactory.toEntity(user)));
    }
	
	public Order save(Order order) {
        return OrderFactory.toComposite(orderRepository.save(OrderFactory.toEntity(order)));
    }
}
