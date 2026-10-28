package com.vsr.service;

import org.springframework.stereotype.Service;
import com.vsr.entity.Order;
import com.vsr.entity.Payment;
import com.vsr.repository.OrderRepository;
import com.vsr.repository.PaymentRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public OrderService(OrderRepository orderRepository, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment findPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }
}