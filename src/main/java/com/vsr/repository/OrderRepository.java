package com.vsr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vsr.entity.Order;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	@SuppressWarnings("unchecked")
	Order save(Order order);	
}
