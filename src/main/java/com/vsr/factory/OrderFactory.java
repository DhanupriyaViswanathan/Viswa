package com.vsr.factory;

import java.util.List;
import java.util.stream.Collectors;

public class OrderFactory {
	public static List<com.vsr.composite.Order> toCompositeList(
            List<com.vsr.entity.Order> orders) {

        return orders.stream()
                .map(OrderFactory::toComposite)
                .collect(Collectors.toList());
    }
	
	public static com.vsr.composite.Order toComposite(
            com.vsr.entity.Order order) {

        com.vsr.composite.Order compositeOrder =
                new com.vsr.composite.Order();
        compositeOrder.setOrderId(order.getOrderId());
        compositeOrder.setProductId(order.getProductId());
        compositeOrder.setUserId(order.getUserId());
        compositeOrder.setQuantity(order.getQuantity());
        compositeOrder.setTotalPrice(order.getTotalPrice()); 
        compositeOrder.setOrderStatus(order.getOrderStatus());
        compositeOrder.setCreatedDate(order.getCreatedDate());
        compositeOrder.setModeOfPayment(order.getModeOfPayment());
        compositeOrder.setUpdatedDate(order.getUpdatedDate());
        compositeOrder.setShipmentSatus(order.getShipmentSatus());
        return compositeOrder;
    }
	
	
	public static com.vsr.entity.Order toEntity(
            com.vsr.composite.Order order) {

        com.vsr.entity.Order entityOrder =
                new com.vsr.entity.Order();
        entityOrder.setOrderId(order.getOrderId());
        entityOrder.setProductId(order.getProductId());
        entityOrder.setUserId(order.getUserId());
        entityOrder.setQuantity(order.getQuantity());
        entityOrder.setTotalPrice(order.getTotalPrice()); 
        entityOrder.setOrderStatus(order.getOrderStatus());
        entityOrder.setCreatedDate(order.getCreatedDate());
        entityOrder.setModeOfPayment(order.getModeOfPayment());
        entityOrder.setUpdatedDate(order.getUpdatedDate());
        entityOrder.setShipmentSatus(order.getShipmentSatus());
        return entityOrder;
    }
	
	
}
