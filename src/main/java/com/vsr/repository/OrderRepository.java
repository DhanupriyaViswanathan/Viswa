package com.vsr.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vsr.entity.Order;


@Repository
public interface OrderRepository extends JpaRepository<Order, BigDecimal>{

	@SuppressWarnings("unchecked")
	Order save(Order order);
	
}
