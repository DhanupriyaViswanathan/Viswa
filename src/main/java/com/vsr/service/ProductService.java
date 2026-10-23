package com.vsr.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vsr.composite.Product;
import com.vsr.factory.ProductFactory;
import com.vsr.repository.ProductRepository;

@Service
public class ProductService {

	private ProductRepository productRepository;
	
	 public ProductService(ProductRepository productRepository) {
	        this.productRepository = productRepository;
	    }
		
		public List<Product> findAll(){
			return ProductFactory.toCompositeList(productRepository.findAll());
		}
}
