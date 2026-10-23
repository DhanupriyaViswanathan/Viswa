package com.vsr.factory;

import java.util.List;
import java.util.stream.Collectors;

public class ProductFactory {
	
	 public static List<com.vsr.composite.Product> toCompositeList(
	            List<com.vsr.entity.Product> products) {

	        return products.stream()
	                .map(ProductFactory::toComposite)
	                .collect(Collectors.toList());
	    }
	 
	 public static com.vsr.composite.Product toComposite(
	            com.vsr.entity.Product product) {

	        com.vsr.composite.Product compositeProduct =
	                new com.vsr.composite.Product();

	        compositeProduct.setProductId(product.getProductId());
	        compositeProduct.setProductName(product.getProductName());
	        compositeProduct.setProductDescription(product.getProductDescription());
	        compositeProduct.setActiveFlag(product.getActiveFlag());
	        compositeProduct.setMakeDate(product.getMakeDate());
	        compositeProduct.setExpeirDate(product.getExpeirDate());
	        compositeProduct.setRate(product.getRate());
	        return compositeProduct;
	    }

}
