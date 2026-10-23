package com.vsr.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vsr.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, BigDecimal>{
	// Get all Products
    List<Product> findAll();
}
