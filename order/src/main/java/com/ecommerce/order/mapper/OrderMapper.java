package com.ecommerce.order.mapper;

import com.ecommerce.order.constants.OrderStatus;
import com.ecommerce.order.dto.OrderCreatedEvent;
import com.ecommerce.order.dto.OrderItemDto;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.model.Order;

public class OrderMapper {

    public static OrderResponse mapToOrderResponse(Order order, OrderResponse orderResponse) {
        orderResponse.setId(order.getId());
        orderResponse.setTotalAmount(order.getTotalAmount());
        orderResponse.setOrderStatus(order.getStatus());
//        orderResponse.setOrderItems(order.getOrderItems().stream()
//                .map(orderItem -> new OrderItemDto(
//                        orderItem.getId(),
//                        orderItem.getProduct().getId(),
//                        orderItem.getQuantity(),
//                        orderItem.getPrice(),
//                        orderItem.getPrice().multiply(java.math.BigDecimal.valueOf(orderItem.getQuantity())
//                ))).toList());
        orderResponse.setOrderItems(OrderItemMapper.mapToOrderItemDtosList(order.getOrderItems()));
        orderResponse.setCreatedAt(order.getCreatedAt());
        return orderResponse;
    }

    public static OrderCreatedEvent mapToOrderCreatedEvent(Order savedOrder, OrderCreatedEvent orderCreatedEvent) {
        orderCreatedEvent.setOrderId(savedOrder.getId());
        orderCreatedEvent.setUserId(savedOrder.getUserId());
        orderCreatedEvent.setStatus(savedOrder.getStatus());
        orderCreatedEvent.setTotalAmount(savedOrder.getTotalAmount());
        orderCreatedEvent.setOrderItems(OrderItemMapper.mapToOrderItemDtosList(savedOrder.getOrderItems()));
        orderCreatedEvent.setCreatedAt(savedOrder.getCreatedAt());
        return orderCreatedEvent;
    }
}
